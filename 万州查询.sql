--  任务性质统计
select distinct et.NameM 事件类型 ,pc.任务编码,pc.任务序号,t.事件编码, 里程 into #dis
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码
	where e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
select 事件类型 outCallType,SUM(里程) distance into #temp1 from #dis d group by 事件类型
select et.NameM outCallType,COUNT(et.NameM) times,
	SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,'' takeBackRate
	into #temp2
	from AuSp120.tb_EventV e
	left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	
	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by et.NameM
select et.NameM  outCallType,isnull(AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)),0) averageResponseTime,
	isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) averageTime,SUM(case when pc.转归编码=7 then 1 else 0 end) refuseToHospitals,
	SUM(case when pc.救治结果编码 in (2,6,7) then 1 else 0 end) deaths,
	SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars into #temp3
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码
	left outer join AuSp120.tb_PatientCase pc on pc.任务编码=t.事件编码 and t.任务序号=pc.任务序号
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by et.NameM
select t2.outCallType,isnull(t1.distance,0) distance,t3.emptyCars,t2.takeBackRate,t2.takeBacks,t2.times,t3.averageResponseTime,t3.averageTime,
	t3.deaths,t3.refuseToHospitals 
	from #temp2 t2	
	left outer join #temp3 t3 on t3.outCallType=t2.outCallType
	left outer join #temp1 t1 on t1.outCallType=t2.outCallType
drop table #temp1,#temp2,#temp3,#dis
--呼救未受理
select  hr.NameM reason,COUNT(*) times,'' rate
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on t.受理序号=a.受理序号 and t.事件编码=a.事件编码
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_DHangReason hr on hr.Code=a.挂起原因编码
	where e.事件性质编码=1 and a.类型编码 in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by hr.NameM
--受理时间统计
select 	m.姓名 dispatcher,(select avg(datediff(Second,tr.振铃时刻,tr.通话开始时刻)) from AuSp120.tb_TeleRecord tr where  m.工号=tr.调度员编码 group by m.工号) averageOffhookTime,avg(datediff(Second, a.开始受理时刻, a.派车时刻)) averageOffSendCar,
	avg(datediff(Second, a.开始受理时刻, a.结束受理时刻)) averageAccept,(select avg(datediff(Second,sl.开始时刻,sl.结束时刻)) from AuSp120.tb_SlinoLog sl where sl.座席状态='就绪' and m.工号=sl.调度员编码 group by m.工号) readyTime,
	(select avg(datediff(Second,sl.开始时刻,sl.结束时刻)) from AuSp120.tb_SlinoLog sl where sl.座席状态='离席' and m.工号=sl.调度员编码 group by m.工号) leaveTime
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_Event e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码
	where e.事件性质编码=1 
	group by m.姓名,m.工号
--其他医院调度分诊
select a.分诊调度医院 station,SUM(a.救治人数) takeBacks into #temp1
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	where e.事件性质编码=1 and a.类型编码 in (11,12) and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	and a.拒绝分诊调度原因编码 is null
	group by a.分诊调度医院
select a.分诊调度医院 station,SUM(case when a.拒绝分诊调度原因编码 is  null then 1 else 0 end) acceptTimes,
	SUM(case when a.拒绝分诊调度原因编码 is not null then 1 else 0 end) noAcceptTimes	into #temp2
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	where e.事件性质编码=1 and a.类型编码 in (11,12) and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by a.分诊调度医院
select t2.station,t2.acceptTimes,t2.noAcceptTimes,isnull(t1.takeBacks,0) takeBacks
	from #temp2 t2 left outer join #temp1 t1 on t1.station=t2.station
drop table #temp1,#temp2
--突发事件统计
select gat.NameM eventType,COUNT(gat.NameM) times  into #temp1
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.事故种类编码
	where e.事件性质编码=1 and e.事故种类编码<>0 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by gat.NameM
select gat.NameM  eventType,COUNT(*) casualties,SUM(case when pc.病情编码=3 then 1 else 0 end) light,
	SUM(case when pc.病情编码=2 then 1 else 0 end) middle,SUM(case when pc.病情编码=5 then 1 else 0 end) heavy,
	SUM(case when pc.救治结果编码 in (2,6,7) then 1 else 0 end) death  into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.事故种类编码
	where e.事件性质编码=1 and e.事故种类编码<>0 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by gat.NameM
select distinct gat.NameM  eventType,pc.里程,e.受理时刻,t.到达现场时刻,t.出车时刻,t.到达医院时刻,t.任务序号,t.任务编码 into #temp3
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.事故种类编码
	where e.事件性质编码=1 and e.事故种类编码<>0  and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select  t3.eventType,SUM(t3.里程) distance,AVG(DATEDIFF(Second,t3.受理时刻,t3.到达现场时刻)) responseTime,
	SUM(DATEDIFF(Second,t3.出车时刻,t3.到达医院时刻)) timeTotal into #temp4
	from #temp3 t3 
	group by t3.eventType
select t1.eventType,t1.times,isnull(t2.casualties,0) casualties,isnull(t2.death,0) death,isnull(t2.heavy,0) heavy,
	isnull(t2.light,0) light,isnull(t2.middle,0) middle,isnull(t4.distance,0) distance,isnull(t4.responseTime,0) responseTime,isnull(t4.timeTotal,0) timeTotal
	from #temp1 t1 left outer join #temp2 t2 on t1.eventType=t2.eventType
	left outer join #temp4 t4  on t1.eventType=t4.eventType
drop table #temp1,#temp2,#temp3,#temp4

--中心接警统计
select distinct e.事件编码 eventCode,s.分站名称 station,m.姓名 dispatcher into #temp1	from AuSp120.tb_EventV e	
	left outer join AuSp120.tb_MrUser m on m.工号=e.调度员编码	left outer join AuSp120.tb_Task t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码 	where e.事件性质编码=1 and m.人员类型=0	
select a.ID id,convert(varchar(20),a.开始受理时刻,120) answerAlarmTime,	a.呼救电话 alarmPhone,a.联系电话 relatedPhone,
	a.现场地址 siteAddress,	a.初步判断 judgementOnPhone, station,	convert(varchar(20),a.派车时刻,120) sendCarTime, dispatcher	
	from #temp1 t	left outer join AuSp120.tb_AcceptDescriptV a on t.eventCode=a.事件编码		
	where a.开始受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
drop table #temp1
--司机工作统计
select t.司机 name, COUNT(*) outCalls,
	SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars,
	SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,	
	SUM(case when pc.救治结果编码=2 then 1 else 0 end) spotDeaths,
	SUM(case when pc.救治结果编码 in (6,7) then 1 else 0 end) afterDeaths,	
	SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransports,
	SUM(case when e.事件类型编码=10 then 1 else 0 end) others,	
	SUM(case when pc.任务编码 is not null then 1 else 0 end) cureNumbers into #temp1	
	from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	
	where e.事件性质编码=1 and t.司机 is not null and t.司机<>'' and e.受理时刻  between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'	
	group by t.司机    
select distinct t.司机 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,	
	e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2	from AuSp120.tb_TaskV  t	
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	
	left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识  	
	where e.事件性质编码=1 and t.司机 is not null and t.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'	  
select t2.name,SUM(t2.里程) distanceTotal,SUM(t2.收费金额) costToal,	
	AVG(DATEDIFF(Second,t2.受理时刻,t2.到达现场时刻)) averageResponseTime,	
	sum(datediff(Second,t2.出车时刻,t2.到达医院时刻)) outCallTimeTotal into #temp3	from #temp2 t2	group by t2.name  
select t1.name,t1.afterDeaths,t1.cureNumbers,t1.emptyCars,t1.inHospitalTransports,t1.takeBacks,t1.others,
	t1.outCalls,t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,
	isnull(t3.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal	from #temp1 t1 
	left outer join #temp3 t3 on t1.name=t3.name  
	drop table #temp1,#temp2,#temp3 
--医生护士司机工作统计
select s.分站名称 station,pc.随车医生 name, COUNT(*) outCalls,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,
	SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars,SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,
	SUM(case when pc.救治结果编码=2 then 1 else 0 end) spotDeaths,SUM(case when pc.救治结果编码 in (6,7) then 1 else 0 end) afterDeaths,
	SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransports,SUM(case when e.事件类型编码=10 then 1 else 0 end) others,
	SUM(case when pc.任务编码 is not null then 1 else 0 end) cureNumbers into #temp1
	from AuSp120.tb_TaskV  t 
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码
	where e.事件性质编码=1 and pc.随车医生 is not null and pc.随车医生<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称,pc.随车医生 	
select distinct s.分站名称 station,pc.随车医生 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,
	e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2
	from AuSp120.tb_TaskV  t 
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码
	left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识
	where e.事件性质编码=1 and pc.随车医生 is not null and pc.随车医生<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select t2.station,t2.name,SUM(t2.里程) distanceTotal,SUM(t2.收费金额) costToal,
	AVG(DATEDIFF(Second,t2.受理时刻,t2.到达现场时刻)) averageResponseTime,
	sum(datediff(Second,t2.出车时刻,t2.到达医院时刻)) outCallTimeTotal into #temp3
	from #temp2 t2
	group by t2.station,t2.name
select t1.station,t1.name,t1.afterDeaths,t1.cureNumbers,t1.emptyCars,t1.inHospitalTransports,t1.takeBacks,
	t1.others,t1.outCalls,t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,
	isnull(t3.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal
	from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station
drop table #temp1,#temp2,#temp3
--司机出诊表
select t.司机 name,COUNT(*) outCalls,SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,
	AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) averageResponseTime,SUM(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) outCallTimeTotal,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) averageTime into #temp1
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and t.司机 is not null and t.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
	group by t.司机
select distinct t.司机 ,pc.任务编码,pc.任务序号,t.事件编码, 里程 into #dis
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and t.司机 is not null and t.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
select 司机 name,SUM(里程) distance into #temp2 from #dis d group by 司机
select t1.name,isnull(t1.averageResponseTime,0) averageResponseTime,isnull(t1.averageTime,0) averageTime,isnull(t1.outCallTimeTotal,0) outCallTimeTotal,
	t1.outCalls,t1.takeBacks,isnull(t2.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name
drop table #dis,#temp1,#temp2
--站点出诊情况
select s.分站名称 station,SUM(case when e.事件类型编码=1 then 1 else 0 end) spotFirstAid,
	SUM(case when e.事件类型编码=2 then 1 else 0 end) stationTransfer,SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransfer,
	SUM(case when e.事件类型编码=4 then 1 else 0 end) sendOutPatient,SUM(case when e.事件类型编码=5 then 1 else 0 end) safeguard,
	SUM(case when e.事件类型编码=6 then 1 else 0 end) auv,SUM(case when e.事件类型编码=7 then 1 else 0 end) volunteer,
	SUM(case when e.事件类型编码=8 then 1 else 0 end) train,SUM(case when e.事件类型编码=9 then 1 else 0 end) practice,
	SUM(case when e.事件类型编码=10 then 1 else 0 end) other,SUM(case when t.任务编码 is not null then 1 else 0 end) outCallTotal,
	SUM(case when t.结果编码=4 then 1 else 0 end) tackBackTotal,SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars,
	SUM(case when t.结果编码=5 then 1 else 0 end) refuses into #temp1
	from AuSp120.tb_EventV e 
	left outer join  AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join  AuSp120.tb_Station s on s.分站编码=t.分站编码
	where e.事件性质编码=1	and t.分站编码 is not null and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称
select s.分站名称 station,SUM(pc.里程) distance,SUM(case when pc.救治结果编码 in (2,6,7) then 1 else 0 end) death into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.任务编码=t.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on s.分站编码=pc.分站编码 
	where e.事件性质编码=1	and pc.分站编码 is not null and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称
select t1.station,t1.spotFirstAid,t1.stationTransfer,t1.inHospitalTransfer,t1.sendOutPatient,t1.safeguard,
	t1.auv,t1.volunteer,t1.train,t1.practice,t1.other,t1.outCallTotal,t1.tackBackTotal,t1.emptyCars,
	t1.refuses,isnull(t2.death,0) death,isnull(t2.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
drop table #temp1,#temp2
--站点任务类型统计
select distinct pc.任务编码,pc.任务序号,pc.里程 into #pc from AuSp120.tb_PatientCase pc
select s.分站名称 station,COUNT(*) ztimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) zaverageResponseTime,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) zaverageTime,SUM(pc.里程) zdistance into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and t.分站编码 is not null and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称
select s.分站名称 station,COUNT(*) xctimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) xcaverageResponseTime,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) xcaverageTime,SUM(pc.里程) xcdistance into #temp2
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and t.分站编码 is not null and e.事件类型编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称
select s.分站名称 station,COUNT(*) yytimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) yyaverageResponseTime,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) yyaverageTime,SUM(pc.里程) yydistance into #temp3
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and t.分站编码 is not null and e.事件类型编码=2 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称
select t1.station,isnull(t1.zaverageResponseTime,0) zaverageResponseTime,
	isnull(t1.zaverageTime,0) zaverageTime,isnull(t1.zdistance,0) zdistance,isnull(t1.ztimes,0) ztimes,isnull(t2.xcaverageResponseTime,0) xcaverageResponseTime,
	isnull(t2.xcaverageTime,0) xcaverageTime,isnull(t2.xcdistance,0) xcdistance,
	isnull(t2.xctimes,0) xctimes,isnull(t3.yyaverageResponseTime,0) yyaverageResponseTime,
	isnull(t3.yyaverageTime,0) yyaverageTime,isnull(t3.yydistance,0) yydistance,isnull(t3.yytimes,0) yytimes 
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
	left outer join #temp3 t3 on t1.station=t3.station
drop table #pc,#temp1,#temp2,#temp3
--医院转诊统计
select distinct pc.任务编码,pc.任务序号,pc.里程,pc.送往地点 into #pc from AuSp120.tb_PatientCase pc
select da.NameM area,pc.送往地点 station,COUNT(*) outCalls,isnull(SUM(case when t.结果编码=4 then 1 else 0 end),0) takeBacks,
	isnull(SUM(pc.里程),0) distance,isnull(sum(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) time
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	left outer join #pc pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DArea da on da.Code=a.区域编码
	where e.事件性质编码=1 and t.分站编码 is not null and e.事件类型编码=2 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM,pc.送往地点
drop table #pc
--医院转诊明细
select CONVERT(varchar(20),e.受理时刻,120) dates,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,pc.医生诊断 diagnose,pc.出诊地址 outCallAddress,pc.科室 sendClass 
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	left outer join AuSp120.tb_PatientCase pc  on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and t.分站编码 is not null and pc.任务编码 is not null and e.事件类型编码=2 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--院内转运统计
select distinct pc.任务编码,pc.任务序号,pc.里程,pc.出诊地址,pc.送往地点 into #pc from AuSp120.tb_PatientCase pc
select s.分站名称 station,COUNT(*) outCalls,isnull(SUM(pc.里程),0) distance,isnull(sum(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) time
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	left outer join #pc pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and t.分站编码 is not null and e.事件类型编码=3 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称
drop table #pc
--院内转诊明细
select CONVERT(varchar(20),e.受理时刻,120) date,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,pc.医生诊断 diagnose,pc.出诊地址 outCallAddress,pc.科室 sendClass 
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	left outer join AuSp120.tb_PatientCase pc  on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and t.分站编码 is not null and pc.任务编码 is not null and e.事件类型编码=3 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--本院内出诊明细
select CONVERT(varchar(20),e.受理时刻,120) date,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,pc.医生诊断 diagnose,pc.出诊地址 outCallAddress,pc.科室 sendClass
	from AuSp120.tb_TaskV t  
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and t.分站编码='001' and pc.任务编码 is not null and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--三无明细查询
select cr.任务编码,cr.任务序号,cr.病历序号,SUM(cr.收费金额) cost into #temp1 from AuSp120.tb_ChargeRecord cr
	group by cr.任务编码,cr.任务序号,cr.病历序号
select pc.任务编码,pc.任务序号,pc.序号,pc.姓名 name,pc.性别 gender,pc.年龄 age,pc.医生诊断 diagnose,pc.现场地点 address,
	pc.送往地点 toAddress,pc.里程 distance into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	where e.事件性质编码=1 and e.受理时刻 between '2015-07-01 00:00:00' and '2015-11-01 00:00:00'
select  t1.cost,t2.age,t2.address,t2.diagnose,t2.distance,t2.gender,t2.name,t2.toAddress 
	from #temp2 t2 left outer join #temp1 t1 on t1.任务序号=t2.任务序号 and t1.任务编码=t2.任务编码 and t1.病历序号=t2.序号
drop table #temp1,#temp2
--区域统计
select distinct pc.任务序号,pc.任务编码,pc.里程 into #pc
	from AuSp120.tb_PatientCase pc
select da.NameM area,COUNT(t.任务编码) outCalls,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,isnull(sum(pc.里程),0) distance,
	isnull(SUM(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) outCallTime,isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) averageTime
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_DArea da on da.Code=a.区域编码
	left outer join #pc pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM
drop table #pc
--24小时出诊强度统计
select  DATEPART(HOUR,e.受理时刻) span,COUNT(*) times,SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,
	isnull(AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)),0) averageResponseTime,isnull(sum(datediff(Second,t.出车时刻,t.到达医院时刻)),0) outCallTotal,
	isnull(avg(datediff(Second,t.出车时刻,t.到达医院时刻)),0) averageTime
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by DATEPART(HOUR,e.受理时刻)
	order by DATEPART(HOUR,e.受理时刻)
--疾病科别统计
select dc.NameM name,isnull(COUNT(*),0) times,'' rate
	from AuSp120.tb_PatientCase pc  left outer join AuSp120.tb_TaskV t on  t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.疾病科别编码
	where e.事件性质编码=1 and pc.疾病科别编码 is not null  and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by dc.NameM
	
--病因统计
select dr.NameM name,isnull(COUNT(*),0) times,'' rate
	from AuSp120.tb_PatientCase pc  left outer join AuSp120.tb_TaskV t on  t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_DDiseaseReason dr on dr.Code=pc.病因编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by dr.NameM
--送往地点统计
select pc.送往地点 address, COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	where pc.记录时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and pc.送往地点 is not null and pc.送往地点<>''
	group by pc.送往地点
--救治结果统计
select dr.NameM name, COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_DResult dr on dr.Code=pc.救治结果编码
	where pc.记录时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by dr.NameM 
--病人性别统计
select pc.性别 gender,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc
	where pc.记录时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by pc.性别
--病情统计
select ds.NameM name, COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_DILLState ds  on ds.Code=pc.病情编码
	where pc.记录时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by ds.NameM
--医疗处置统计
select dm.NameM name,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_CureMeasure cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号
	left outer join AuSp120.tb_DMeasure dm on dm.Code=cm.救治措施编码
	where pc.记录时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and dm.NameM is not null
	group by dm.NameM
--医生护士司机误餐统计
select pc.随车医生 name,COUNT(*) times, 
	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   rate 
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.任务编码 is not null and pc.随车医生<>''
	group by pc.随车医生
union 
select pc.随车护士 name,COUNT(*) times, 
	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   rate 
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.任务编码 is not null and pc.随车护士<>''
	group by pc.随车护士
union
select pc.司机 name,COUNT(*) times, 
	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   rate 
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.任务编码 is not null and pc.司机<>''
	group by pc.司机
--分诊他院拒绝受理原因统计
select a.分诊调度医院 station,COUNT(*) totals into #temp1
	from AuSp120.tb_AcceptDescriptV a left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	left outer join AuSp120.tb_DTriageRefuse dtr on dtr.Code=a.拒绝分诊调度原因编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and a.类型编码 in (11,12)
	group by a.分诊调度医院
	order by a.分诊调度医院
select a.分诊调度医院 station,dtr.NameM reason,SUM(case when a.拒绝分诊调度原因编码 is not null then 1 else 0 end) times into #temp2
	from AuSp120.tb_AcceptDescriptV a left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	left outer join AuSp120.tb_DTriageRefuse dtr on dtr.Code=a.拒绝分诊调度原因编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and a.类型编码 in (11,12) and dtr.NameM is not null
	group by a.分诊调度医院,dtr.NameM
	order by a.分诊调度医院,dtr.NameM
select t1.station,t1.totals,t2.reason,t2.times
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
drop table #temp1,#temp2
--非急救用车统计
select distinct pc.任务编码,pc.任务序号,pc.里程 into #pc from AuSp120.tb_PatientCase pc 
select 'ids' ids, COUNT(*) hospital_times,sum(pc.里程) hospital_distance into #temp1
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=3
select 'ids' ids, COUNT(*) safeguard_times,sum(pc.里程) safeguard_distance into #temp2
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=5
select 'ids' ids,COUNT(*) clinic_times,sum(pc.里程) clinic_distance into #temp3
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=7
select 'ids' ids,COUNT(*) practice_times,sum(pc.里程) practice_distance into #temp4
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码 in (8,9)
select 'ids' ids,COUNT(*) inHospital_times,sum(pc.里程) inHospital_distance into #temp5
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=3 and t.分站编码='001'
select 'ids' ids,COUNT(*) xh_times,sum(pc.里程) xh_distance into #temp6
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=6
select 'ids' ids,COUNT(*) other_times,sum(pc.里程) other_distance into #temp7
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=10
select ISNULL(t1.hospital_times,0) hospital_times,ISNULL(t1.hospital_distance,0) hospital_distance,ISNULL(t2.safeguard_distance,0) safeguard_distance,ISNULL(t2.safeguard_times,0) safeguard_times,
	ISNULL(t3.clinic_distance,0) clinic_distance,ISNULL(t3.clinic_times,0) clinic_times,ISNULL(t4.practice_distance,0) practice_distance,ISNULL(t4.practice_times,0) practice_times,
	ISNULL(t5.inHospital_distance,0) inHospital_distance,ISNULL(t5.inHospital_times,0) inHospital_times,ISNULL(t6.xh_distance,0) xh_distance,ISNULL(t6.xh_times,0) xh_times,
	ISNULL(t7.other_distance,0) other_distance,ISNULL(t7.other_times,0) other_times
	from #temp1 t1 left outer join #temp2 t2 on t1.ids=t2.ids
	left outer join #temp3 t3 on t2.ids=t3.ids
	left outer join #temp4 t4 on t3.ids=t4.ids
	left outer join #temp5 t5 on t4.ids=t5.ids
	left outer join #temp6 t6 on t5.ids=t6.ids
	left outer join #temp7 t7 on t6.ids=t7.ids
drop table #pc,#temp1,#temp2,#temp3,#temp4,#temp5,#temp6,#temp7
--急救保障明细
select distinct pc.任务编码,pc.任务序号,pc.里程,pc.随车医生,pc.随车护士,pc.司机 into #pc from AuSp120.tb_PatientCase pc
select CONVERT(varchar(20),e.受理时刻,120) date,det.NameM nature,e.事件名称 event,a.现场地址 address,
	isnull(pc.里程,0) distance,pc.随车医生 doctor,pc.随车护士 nurse,pc.司机 driver 
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码 in (1,5)
drop table #pc
--病人年龄段统计
select pc.年龄 span,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	where pc.记录时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and ISNUMERIC(pc.年龄)=0 group by pc.年龄
union
select case when CAST(pc.年龄 as float) between 0 and 10 then '0~10岁' 
	when CAST(pc.年龄 as float) between 10 and 20 then '10~20岁'
	when CAST(pc.年龄 as float) between 20 and 30 then '20~30岁'
	when CAST(pc.年龄 as float) between 30 and 40 then '30~40岁'
	when CAST(pc.年龄 as float) between 40 and 50 then '40~50岁'
	when CAST(pc.年龄 as float) between 50 and 60 then '50~60岁'
	when CAST(pc.年龄 as float) between 60 and 70 then '60~70岁'
	when CAST(pc.年龄 as float) between 70 and 80 then '70~80岁'
	when CAST(pc.年龄 as float) between 80 and 90 then '80~90岁'
	else '90~' end span,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	where  ISNUMERIC(pc.年龄)=1 and CAST(pc.年龄 as float)>10 
	and pc.记录时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by case when CAST(pc.年龄 as float) between 0 and 10 then '0~10岁' 
	when CAST(pc.年龄 as float) between 10 and 20 then '10~20岁'
	when CAST(pc.年龄 as float) between 20 and 30 then '20~30岁'
	when CAST(pc.年龄 as float) between 30 and 40 then '30~40岁'
	when CAST(pc.年龄 as float) between 40 and 50 then '40~50岁'
	when CAST(pc.年龄 as float) between 50 and 60 then '50~60岁'
	when CAST(pc.年龄 as float) between 60 and 70 then '60~70岁'
	when CAST(pc.年龄 as float) between 70 and 80 then '70~80岁'
	when CAST(pc.年龄 as float) between 80 and 90 then '80~90岁'
	else '90~' end
--调度员工作统计
select m.姓名,COUNT(*) 电话总数,sum(case when tr.记录类型编码 in(1,2,3,5,8) then 1 else 0 end) 呼入,
	sum(case when tr.记录类型编码=6 then 1 else 0 end) 呼出 into #temp1
	from AuSp120.tb_TeleRecord tr left outer join AuSp120.tb_DTeleRecordType drt on drt.Code=tr.记录类型编码
	left outer join AuSp120.tb_MrUser m on m.工号=tr.调度员编码 
	where not (tr.调度员编码='' or tr.调度员编码 is null ) and  m.人员类型=0 and tr.记录类型编码 in(1,2,3,5,6,8) and tr.产生时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by m.姓名	
select m.姓名,a.类型编码,a.救治人数,a.调度员编码,t.结果编码,a.开始受理时刻,t.事件编码,a.派车时刻,t.任务序号,t.任务编码 into #temp2 
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.受理序号=t.受理序号 and a.事件编码=t.事件编码
	left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码
	where e.事件性质编码=1 and m.人员类型=0 and a.开始受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select t2.姓名,SUM(t2.救治人数) 分诊数 into #temp5 from #temp2 t2 where t2.类型编码 in (11,12) group by t2.姓名
select t2.姓名,SUM(case when t2.开始受理时刻 is not null and t2.派车时刻 is not null then 1 else 0 end) 有效派车,
	SUM(case when t2.结果编码=4 then 1 else 0 end)正常完成,	SUM(case when t2.结果编码=3 then 1 else 0 end) 空车,
	SUM(case when t2.结果编码=2 then 1 else 0 end) 中止任务,SUM(case when t2.结果编码=5 then 1 else 0 end) 拒绝出车 into #temp3 
	from #temp2 t2 group by t2.姓名
select t2.姓名,SUM(case when pc.转归编码=1 then 1 else 0 end) 救治人数 into #temp4 from  AuSp120.tb_PatientCase pc
	left outer join #temp2 t2  on pc.任务序号=t2.任务序号 and t2.任务编码=pc.任务编码 group by t2.姓名
select t1.姓名 dispatcher,t1.电话总数 numbersOfPhone,t1.呼入 inOfPhone,t1.呼出 outOfPhone,isnull(t3.有效派车,0) numbersOfSendCar,
	isnull(t3.正常完成,0) numbersOfNormalSendCar,isnull(t3.空车,0) emptyCar,isnull(t3.中止任务,0) numbersOfStopTask,
	isnull(t3.拒绝出车,0) refuseCar,isnull(t4.救治人数,0) takeBacks,isnull(t5.分诊数,0) triageNumber from #temp1 t1 
	left outer join #temp3 t3 on t1.姓名=t3.姓名 left outer join #temp4 t4 on t1.姓名=t4.姓名 left outer join #temp5 t5 on t5.姓名=t1.姓名
drop table #temp1,#temp2,#temp3,#temp4,#temp5
--医生护士出诊明细




select * from AuSp120.tb_DTeleRecordResult
select * from AuSp120.tb_TeleRecord
select * from AuSp120.tb_DTeleRecordType
select * from AuSp120.tb_DTaskResult ac
select * from AuSp120.tb_AcceptDescript a where a.类型编码 in (11,12)
select * from AuSp120.tb_TaskV t where t.事件编码='2015061510551801'
select * from AuSp120.tb_DILLState
select * from AuSp120.tb_DTriageRefuse
select * from AuSp120.tb_PatientCase pc 
select * from AuSp120.tb_EventV e where e.事故种类编码<>0
select * from AuSp120.tb_DOutCome
<<<<<<< HEAD
select * from AuSp120.tb_DArea
=======
select * from AuSp120.tb_DAcceptDescriptType
>>>>>>> 0868d3db9dc932e6ec2d8d03f0b31e6d22eb3264
select * from AuSp120.tb_MrUser




	
