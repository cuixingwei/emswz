--  任务性质统计
select distinct et.NameM 事件类型 ,pc.任务编码,pc.任务序号,t.事件编码, 里程,a.区域编码 into #dis
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
select 事件类型 outCallType,SUM(里程) distance,COUNT(事件类型) times,SUM(case when 区域编码=1 then 1 else 0 end) shiqu,
	SUM(case when 区域编码=2 then 1 else 0 end) wanzhou,SUM(case when 区域编码 not in (1,2) then 1 else 0 end) others into #temp1 from #dis d group by 事件类型
select et.NameM  outCallType,isnull(AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)),0) averageResponseTime,
	isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) averageTime,SUM(case when pc.转归编码=7 then 1 else 0 end) refuseToHospitals,
	SUM(case when pc.转归编码 in (5,6) then 1 else 0 end) deaths,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,COUNT(pc.转归编码) takeBackRate,
	SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars into #temp3
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by et.NameM
select t1.outCallType,isnull(t1.distance,0) distance,t3.emptyCars,t3.takeBackRate,t3.takeBacks,t1.times,t3.averageResponseTime,t3.averageTime,
	t3.deaths,t3.refuseToHospitals,ISNULL(t1.shiqu,0) shiqu,isnull(t1.wanzhou,0) wanzhou,ISNULL(t1.others,0) others
	from #temp3 t3	
	left outer join #temp1 t1 on t3.outCallType=t1.outCallType
drop table #temp1,#temp3,#dis
--呼救未受理
select hr.NameM reason,COUNT(*) times,'' rate
	from AuSp120.tb_EventV e  left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码
	left outer join AuSp120.tb_TaskV t on t.受理序号=a.受理序号 and t.事件编码=a.事件编码
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
	where e.事件性质编码=1 and a.类型编码 in (11,12,13) and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	and a.拒绝分诊调度原因编码 is null
	group by a.分诊调度医院
select a.分诊调度医院 station,SUM(case when a.拒绝分诊调度原因编码 is  null then 1 else 0 end) acceptTimes,
	SUM(case when a.拒绝分诊调度原因编码 is not null then 1 else 0 end) noAcceptTimes	into #temp2
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	where e.事件性质编码=1 and a.类型编码 in (11,12,13) and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
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
select t.事件编码,t.受理序号,dr.NameM outResult,pc.出诊地址,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks into #temp1
	from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_Task t on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 
	left outer join AuSp120.tb_DResult dr on pc.救治结果编码=dr.Code
	group by t.事件编码,dr.NameM,pc.出诊地址,t.受理序号
select convert(varchar(20),e.受理时刻,120) answerAlarmTime,m.姓名 dispatcher,a.呼救电话 alarmPhone,a.联系电话 relatedPhone,a.分诊调度医院 triageStation,
	a.现场地址 siteAddress,	a.初步判断 judgementOnPhone,t1.出诊地址 station,t1.outResult,t1.takeBacks,convert(varchar(20),a.派车时刻,120) sendCarTime
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码
	left outer join #temp1 t1 on t1.事件编码=a.事件编码 and t1.受理序号=a.受理序号
	left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码
	where e.事件性质编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
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
select distinct 任务编码,病例序号,任务序号 into #cm from AuSp120.tb_CureMeasure
select s.分站名称 station,pc.司机 name,SUM(case when cm.ID is not null then 1 else 0 end) cureNumbers  into #temp6
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_CureMeasure cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称,pc.司机 
select s.分站名称 station,pc.司机 name, SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,
	SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars,SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,
	SUM(case when pc.转归编码=5 then 1 else 0 end) spotDeaths,SUM(case when pc.转归编码=6 then 1 else 0 end) afterDeaths,
	SUM(case when pc.转归编码=8 then 1 else 0 end) others,SUM(case when pc.转归编码=12 then 1 else 0 end) safeOut,
	SUM(case when pc.转归编码=11 then 1 else 0 end) noAmbulance into #temp1
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join #cm cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.分站名称,pc.司机 	
select distinct s.分站名称 station,pc.司机 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,t.接收命令时刻,
	e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码
	left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select distinct s.分站名称 station,pc.司机 name,pc.里程,t.接收命令时刻,
	e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp4
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select t2.station,t2.name,SUM(t2.收费金额) costToal into #temp5
	from #temp2 t2
	group by t2.station,t2.name
select t4.station,t4.name,SUM(t4.里程) distanceTotal,COUNT(*) outCalls,
	AVG(DATEDIFF(Second,t4.受理时刻,t4.到达现场时刻)) averageResponseTime,
	AVG(DATEDIFF(Second,t4.接收命令时刻,t4.出车时刻)) averageSendTime,
	sum(datediff(Second,t4.出车时刻,t4.到达医院时刻)) outCallTimeTotal into #temp3
	from #temp4 t4
	group by t4.station,t4.name
select t1.station,t1.name,t1.afterDeaths,t6.cureNumbers,t1.emptyCars,t1.safeOut,t1.takeBacks,t1.noAmbulance,t1.others,t3.outCalls,
	t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,isnull(t3.averageSendTime,0) averageSendTime,
	isnull(t5.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal
	from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station
	left outer join #temp5 t5 on t1.name=t5.name and t1.station=t5.station 
	left outer join #temp6 t6 on t1.name=t6.name and t1.station=t6.station order by t1.station
drop table #temp1,#temp2,#temp3,#temp4,#temp5,#cm,#temp6
--司机出诊表
select distinct pc.司机 ,pc.任务编码,pc.任务序号, 里程 into #pc from AuSp120.tb_PatientCase pc
select pc.司机 name,COUNT(*) outCalls,	AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) averageResponseTime,
	SUM(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) outCallTimeTotal,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) averageTime,SUM(里程) distance  into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码
	left outer join	AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join #pc pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
	group by pc.司机
select pc.司机 name,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks into #temp2 from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_TaskV t on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
	group by pc.司机
select t1.name,isnull(t1.averageResponseTime,0) averageResponseTime,isnull(t1.averageTime,0) averageTime,isnull(t1.outCallTimeTotal,0) outCallTimeTotal,
	t1.outCalls,t2.takeBacks,isnull(t1.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name
drop table #pc,#temp1,#temp2
--站点出诊情况
select distinct pc.任务序号,pc.任务编码,pc.出诊地址,pc.里程 into #pc from AuSp120.tb_PatientCase pc
select pc.出诊地址 station,SUM(case when e.事件类型编码=1 then 1 else 0 end) spotFirstAid,SUM(pc.里程) distance,
	SUM(case when e.事件类型编码=2 then 1 else 0 end) stationTransfer,SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransfer,
	SUM(case when e.事件类型编码 in (12,13) then 1 else 0 end) sendOutPatient,SUM(case when e.事件类型编码=5 then 1 else 0 end) safeguard,
	SUM(case when e.事件类型编码=6 then 1 else 0 end) auv,SUM(case when e.事件类型编码=7 then 1 else 0 end) volunteer,
	SUM(case when e.事件类型编码=8 then 1 else 0 end) train,SUM(case when e.事件类型编码=9 then 1 else 0 end) practice,
	SUM(case when e.事件类型编码=10 then 1 else 0 end) other,COUNT(*) outCallTotal into #temp1
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码
	left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.出诊地址
select pc.出诊地址 station,SUM(case when pc.转归编码 in (5,6) then 1 else 0 end) death,
	SUM(case when pc.转归编码=1 then 1 else 0 end) tackBackTotal,SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars,
	SUM(case when pc.转归编码=7 then 1 else 0 end) refuses into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.任务编码=t.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_AcceptDescriptV a on t.事件编码=a.事件编码 and t.受理序号=a.受理序号
	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4)	and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.出诊地址
select t1.station,t1.spotFirstAid,t1.stationTransfer,t1.inHospitalTransfer,t1.sendOutPatient,t1.safeguard,
	t1.auv,t1.volunteer,t1.train,t1.practice,t1.other,t1.outCallTotal,t2.tackBackTotal,t2.emptyCars,
	t2.refuses,isnull(t2.death,0) death,isnull(t1.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
	where t1.station is not null and t1.station<>''
drop table #temp1,#temp2,#pc
--站点任务类型统计
select distinct pc.任务编码,pc.任务序号,pc.里程,pc.出诊地址 into #pc from AuSp120.tb_PatientCase pc
select pc.出诊地址  station,COUNT(*) ztimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) zaverageResponseTime,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) zaverageTime,SUM(pc.里程) zdistance into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 
	left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.出诊地址 
select pc.出诊地址 station,COUNT(*) xctimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) xcaverageResponseTime,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) xcaverageTime,SUM(pc.里程) xcdistance into #temp2
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 
	left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.事件类型编码=1 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.出诊地址
select pc.出诊地址 station,COUNT(*) yytimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) yyaverageResponseTime,
	avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) yyaverageTime,SUM(pc.里程) yydistance into #temp3
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 
	left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.事件类型编码=2 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.出诊地址
select t1.station,isnull(t1.zaverageResponseTime,0) zaverageResponseTime,
	isnull(t1.zaverageTime,0) zaverageTime,isnull(t1.zdistance,0) zdistance,isnull(t1.ztimes,0) ztimes,isnull(t2.xcaverageResponseTime,0) xcaverageResponseTime,
	isnull(t2.xcaverageTime,0) xcaverageTime,isnull(t2.xcdistance,0) xcdistance,
	isnull(t2.xctimes,0) xctimes,isnull(t3.yyaverageResponseTime,0) yyaverageResponseTime,
	isnull(t3.yyaverageTime,0) yyaverageTime,isnull(t3.yydistance,0) yydistance,isnull(t3.yytimes,0) yytimes 
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
	left outer join #temp3 t3 on t1.station=t3.station  where t1.station is not null and t1.station<>''
drop table #pc,#temp1,#temp2,#temp3
--医院转诊统计
select distinct pc.任务编码,pc.任务序号,pc.里程 into #pc from AuSp120.tb_PatientCase pc
select da.NameM area ,a.现场地址 station,COUNT(*) outCalls,
	isnull(SUM(pc.里程),0) distance,isnull(sum(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) time into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join #pc pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DArea da on da.Code=a.区域编码
	where e.事件性质编码=1  and a.类型编码 not in (2,4)  and e.事件类型编码=2 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM,a.现场地址
select da.NameM area ,a.现场地址 station,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks into #temp2
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DArea da on da.Code=a.区域编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4)  and e.事件类型编码=2 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM,a.现场地址
select t1.area,t1.station,t1.distance,t1.outCalls,t2.takeBacks,t1.time 
	from #temp1 t1 left outer join #temp2 t2 on t1.area=t2.area and t1.station=t2.station order by t1.area
drop table #pc,#temp1,#temp2
--医院转诊明细
select CONVERT(varchar(20),e.受理时刻,120) dates,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,pc.医生诊断 diagnose,
	pc.出诊地址 outCallAddress,pc.科室 sendClass,a.现场地址 spotAddress,pc.里程 distance,dr.NameM outResult
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_PatientCase pc  on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DResult dr on dr.Code=pc.救治结果编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4)  and e.事件类型编码=2 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--院内转运统计
select distinct pc.任务编码,pc.任务序号,pc.里程,pc.出诊地址 into #pc from AuSp120.tb_PatientCase pc
select pc.出诊地址 station,COUNT(*) outCalls,isnull(SUM(pc.里程),0) distance,isnull(sum(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) time
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join #pc pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and pc.出诊地址 is not null and pc.出诊地址<>'' and a.类型编码 not in (2,4) and e.事件类型编码=3 and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.出诊地址
drop table #pc
--院内转运明细
select CONVERT(varchar(20),e.受理时刻,120) date,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,
	pc.医生诊断 diagnose,pc.出诊地址 outCallAddress,pc.科室 sendClass,a.现场地址 spot,pc.里程 distance,
	DATEDIFF(Second,t.出车时刻,t.到达医院时刻) time
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码
	left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号
	left outer join AuSp120.tb_PatientCase pc  on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4)   
	and e.事件类型编码=3 and pc.任务编码 is not null and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	
--本院内出诊明细
select CONVERT(varchar(20),e.受理时刻,120) date,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,pc.医生诊断 diagnose,pc.现场地点 outCallAddress,pc.科室 sendClass
	from AuSp120.tb_TaskV t  
	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and t.分站编码='001' and pc.任务编码 is not null and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--三无明细查询
select cr.任务编码,cr.任务序号,cr.病历序号,SUM(cr.收费金额) cost into #temp1 from AuSp120.tb_ChargeRecord cr
	group by cr.任务编码,cr.任务序号,cr.病历序号
select pc.任务编码,pc.任务序号,pc.序号,pc.姓名 name,pc.性别 gender,pc.年龄 age,pc.医生诊断 diagnose,pc.现场地点 address,
	pc.送往地点 toAddress,pc.里程 distance into #temp2
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.三无标志=1 and e.受理时刻 between '2015-07-01 00:00:00' and '2015-11-01 00:00:00'
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
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码
	left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
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
select distinct 任务序号,任务编码,随车医生,随车护士,司机 into #pc from AuSp120.tb_PatientCase  
select pc.随车医生 name,COUNT(*) rate, 
	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   times 
	from #pc pc left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.任务编码 is not null and pc.随车医生<>''
	group by pc.随车医生
union 
select pc.随车护士 name,COUNT(*) rate, 
	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   times 
	from #pc pc left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.任务编码 is not null and pc.随车护士<>''
	group by pc.随车护士
union
select pc.司机 name,COUNT(*) rate, 
	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   times 
	from #pc pc left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.任务编码 is not null and pc.司机<>''
	group by pc.司机
drop table #pc
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
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=3
select 'ids' ids, COUNT(*) safeguard_times,sum(pc.里程) safeguard_distance into #temp2
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=5
select 'ids' ids,COUNT(*) clinic_times,sum(pc.里程) clinic_distance into #temp3
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=7
select 'ids' ids,COUNT(*) practice_times,sum(pc.里程) practice_distance into #temp4
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码 in (8,9)
select 'ids' ids,COUNT(*) inHospital_times,sum(pc.里程) inHospital_distance into #temp5
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=3 and t.分站编码='001'
select 'ids' ids,COUNT(*) xh_times,sum(pc.里程) xh_distance into #temp6
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=6
select 'ids' ids,COUNT(*) other_times,sum(pc.里程) other_distance into #temp7
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=10
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
select CONVERT(varchar(20),e.受理时刻,120) date,det.NameM nature,a.初步判断 event,a.现场地址 address,
	isnull(pc.里程,0) distance,pc.随车医生 doctor,pc.随车护士 nurse,pc.司机 driver,DATEDIFF(SECOND,t.出车时刻,t.到达医院时刻) safeTime
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.事件类型编码=5
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
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and m.人员类型=0 and a.开始受理时刻 between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
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
--三峡中心医院出诊明细表
select convert(varchar(20),e.受理时刻,120) dateTime,pc.姓名 patientName,pc.现场地点 address,pc.出诊地址 outStation, 
	dr.NameM outResult,pc.随车医生 doctor,pc.随车护士 nurse,pc.里程 distance,	pc.司机 driver,pc.性别 sex,pc.年龄 age, 
	pc.医生诊断 diagnose,pc.送往地点 sendAddress,da.NameM area,dc.NameM diseaseDepartment,dcs.NameM classState,dis.NameM diseaseDegree,
	de.NameM treatmentEffet,et.NameM eventType,t.车辆编码 carCode,DATEDIFF(Second,t.接收命令时刻,t.到达现场时刻) poorTime,
	DATEDIFF(Second,t.出车时刻,t.完成时刻) userTime,AuSp120.GetCureMeasure(pc.任务编码,pc.序号) cureMeasure,u.姓名 dispatcher
	from AuSp120.tb_PatientCase pc  
	left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码 	 
	left outer join AuSp120.tb_DResult dr on dr.Code=t.结果编码	 
	left outer join AuSp120.tb_AcceptDescriptV  a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码	 
	left outer join AuSp120.tb_DArea da on da.Code=a.区域编码  
	left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.疾病科别编码	 
	left outer join AuSp120.tb_DDiseaseClassState dcs on dcs.Code=pc.分类统计编码	 
	left outer join AuSp120.tb_DILLState dis on dis.Code=pc.病情编码	 
	left outer join AuSp120.tb_DEffect de on de.Code=pc.救治效果编码
	left outer join AuSp120.tb_MrUser u on u.工号=t.调度员编码
	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	 
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.出诊地址 in ('三峡中心医院急救分院','三峡中心医院百安分院','三峡中心医院江南分院') 
	and e.受理时刻 between  '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
--车辆工作情况统计
select 实际标识 carCode,count( p.车辆编码) as pauseNumbers into #temp1 	
	from AuSp120.tb_RecordPauseReason p left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码 	
	where p.操作时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' group by (实际标识) 
select am.实际标识 carCode,t.出车时刻 ,t.到达现场时刻,t.生成任务时刻,结果编码 into #temp2	
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	
	left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码 	
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and t.生成任务时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
select carCode,AVG(DATEDIFF(Second,生成任务时刻,出车时刻)) averageOutCarTimes into #temp3 	
	from #temp2 where 生成任务时刻<出车时刻 group by carCode	
select carCode,AVG(DATEDIFF(Second,出车时刻,到达现场时刻)) averageArriveSpotTimes into #temp4	
	from #temp2 where 出车时刻<到达现场时刻 and 出车时刻 is not null group by carCode 
select t.carCode,sum(case when t.出车时刻 is not null then 1 else 0 end) outCarNumbers,
	sum(case when t.到达现场时刻 is not null then 1 else 0 end) arriveSpotNumbers into #temp5	
	from #temp2 t group by t.carCode 
select distinct pc.任务编码,pc.任务序号,pc.里程,pc.车辆标识 into #pc from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	where t.生成任务时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select pc.车辆标识 carCode,sum(pc.里程) outDistance into #dis1 from #pc pc group by pc.车辆标识
select gv.实际标识 carCode,(MAX(gv.里程)-MIN(里程)) distance into #dis2 from AuSp120.tb_GPSInfoV gv 
	where gv.时间  between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' and gv.里程 <>0  group by gv.实际标识
select t5.carCode,outCarNumbers,averageOutCarTimes,arriveSpotNumbers,averageArriveSpotTimes,isnull(pauseNumbers,0) pauseNumbers,d1.outDistance,d2.distance
	from  #temp5  t5  	
	left outer join #temp1 t1 on  t1.carCode=t5.carCode	
	left outer join #temp3 t3 on t3.carCode=t5.carCode	
	left outer join #temp4 t4 on t5.carCode=t4.carCode	
	left outer join #dis1 d1 on d1.carCode=t5.carCode
	left outer join #dis2 d2 on d2.carCode=t5.carCode
	where t5.carCode is not null	
drop table #temp1,#temp2,#temp3,#temp4,#temp5,#dis1,#dis2,#pc
--放空车统计
select convert(varchar(20),a.开始受理时刻,120) acceptTime,a.现场地址 sickAddress, 
	m.姓名 dispatcher,	isnull(DATEDIFF(Second,t.出车时刻,t.途中待命时刻),0) emptyRunTimes,der.NameM emptyReason,et.NameM eventType	 
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码		 
	left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号	 
	left outer join AuSp120.tb_DEmptyReason der on der.Code=t.放空车原因编码  	 
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and t.结果编码=3 and t.放空车原因编码 is not null 
	and a.开始受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
--挂起事件流水统计
select a.事件编码,a.受理序号,e.事件名称 eventName,dat.NameM acceptType,CONVERT(varchar(20),a.开始受理时刻,120) hungTime,da.NameM area,et.NameM eventType, 
	dhr.NameM hungReason,m.姓名 dispatcher,CONVERT(varchar(20),a.结束受理时刻,120) endTime,
	ISNULL(DATEDIFF(Second,a.开始受理时刻,a.结束受理时刻),0) hungtimes,a.分诊调度医院 station into #temp1	 
	from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码	 
	left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.挂起原因编码	 
	left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	 
	left outer join AuSp120.tb_DArea da on da.Code=a.区域编码
	left outer join AuSp120.tb_DEventType et on e.事件类型编码=et.Code
	left outer join AuSp120.tb_MrUser m on m.工号=e.调度员编码	where e.事件性质编码=1  
	and a.开始受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'  and a.挂起原因编码 is not null
select a.事件编码,a.受理序号,er.NameM 事件结果 into #temp2
	from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码
	left outer join AuSp120.tb_DEventResult er on er.Code=e.事件结果编码
	where e.事件性质编码=1 and a.挂起原因编码 is null  and a.开始受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'  
	and a.事件编码 in (select a.事件编码	from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码 where e.事件性质编码=1 and a.挂起原因编码 is not null)
select distinct t1.acceptType,t1.area,t1.dispatcher,t1.endTime,t1.eventName,t1.eventType,t1.hungReason,t1.hungTime,
	t1.hungtimes,t1.station,t2.事件结果 result
	from #temp1 t1 left outer join #temp2 t2 on t1.事件编码=t2.事件编码 and t2.受理序号>t1.受理序号
drop table #temp1,#temp2
--急救站3分钟未出车情况统计表
select distinct pc.随车医生,pc.随车护士,pc.司机,pc.任务序号,pc.任务编码 into #pc from AuSp120.tb_PatientCase pc 
select a.现场地址 siteAddress,det.NameM eventType,am.实际标识 carCode,CONVERT(varchar(20),a.开始受理时刻,120) acceptTime, 
	CONVERT(varchar(20),t.接收命令时刻,120) acceptTaskTime,	CONVERT(varchar(20),t.出车时刻,120) outCarTime, 
	DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻) outCarTimes,	dtr.NameM taskResult,t.备注 remark,m.姓名 dispatcher,pc.司机 driver,
	pc.随车医生 docter,pc.随车护士 nurse,s.分站名称 station	 
	from AuSp120.tb_TaskV t	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and t.受理序号=a.受理序号 	 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	
	left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 
	left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	 
	left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码	 
	left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.结果编码	
	left outer join AuSp120.tb_Station s on t.分站编码 =s.分站编码
	left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	
	where e.事件性质编码=1 and a.类型编码 not in (2,4) 	and t.生成任务时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
	and DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180
drop table #pc
--出诊时间大于3分钟统计表
select det.NameM outType,SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)<=180 then 1 else 0 end) normalNumbers,
	'' rate1,SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180 then 1 else 0 end) lateNumbers,'' rate2,
	COUNT(*) total
	from AuSp120.tb_TaskV t	
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and t.受理序号=a.受理序号 	 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) 
	and t.生成任务时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by det.NameM
--中心业务数据统计
select 'data' data,SUM(case when 联动来源编码=1 then 1 else 0 end) helpPhone,SUM(case when 联动来源编码=2 then 1 else 0 end) phoneOf110,
	SUM(case when 联动来源编码=3 then 1 else 0 end) phoneOf119,SUM(case when 联动来源编码=4 then 1 else 0 end) phoneOfOther into #temp1
	from AuSp120.tb_Event where 事件性质编码=1 and 受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,SUM(case when 记录类型编码 in (1,2,3,5,8) then 1 else 0 end) inPhone,
	SUM(case when 记录类型编码=6 then 1 else 0 end) outPhone,COUNT(*) totalPhone into #temp2
	from AuSp120.tb_TeleRecord where 产生时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,SUM(case when e.事件类型编码=1 then 1 else 0 end) spotFirstAid,
	SUM(case when e.事件类型编码=2 then 1 else 0 end) stationTransfer,
	SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransfer,
	SUM(case when e.事件类型编码=5 then 1 else 0 end) safeguard,
	SUM(case when e.事件类型编码 not in (1,2,3,5) then 1 else 0 end) noFirstAid,
	SUM(case when t.结果编码=2 then 1 else 0 end) stopStaskNumbers,
	SUM(case when a.类型编码 in (2,4) then 1 else 0 end) hungNumbers,
	SUM(case when a.类型编码 in (11,12,13) then 1 else 0 end) referralSendCar,
	SUM(case when a.类型编码 in (2,4,11,12,13) then 1 else 0 end) centerSendCar into #temp3
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescript a on e.事件编码=a.事件编码
	left outer join AuSp120.tb_TaskV t on a.受理序号=t.受理序号 and a.事件编码=t.事件编码
	where e.事件性质编码=1 and 受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,AVG(DATEDIFF(second,a.开始受理时刻,a.结束受理时刻)) firstaidAcceptTime into #temp4
	from AuSp120.tb_Event e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码
	where e.事件性质编码=1 and e.事件类型编码=1 and 受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,AVG(DATEDIFF(second,a.开始受理时刻,a.结束受理时刻))  referralAcceptTime into #temp5
	from AuSp120.tb_Event e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码
	where e.事件性质编码=1 and e.事件类型编码=2 and 受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select isnull(t1.helpPhone,0) helpPhone,isnull(t1.phoneOf110,0) phoneOf110,isnull(t1.phoneOf119,0) phoneOf119,isnull(t1.phoneOfOther,0) phoneOfOther,isnull(t2.inPhone,0) inPhone,
	isnull(t2.outPhone,0) outPhone,isnull(t2.totalPhone,0) totalPhone,isnull(t3.centerSendCar,0) centerSendCar,isnull(t3.hungNumbers,0) hungNumbers,
	isnull(t3.inHospitalTransfer,0) inHospitalTransfer,isnull(t3.noFirstAid,0) noFirstAid,isnull(t3.referralSendCar,0) referralSendCar,
	isnull(t3.safeguard,0) safeguard,isnull(t3.spotFirstAid,0) spotFirstAid,isnull(t3.stationTransfer,0) stationTransfer,
	isnull(t3.stopStaskNumbers,0) stopStaskNumbers,isnull(t4.firstaidAcceptTime,0) firstaidAcceptTime,isnull(t5.referralAcceptTime,0) referralAcceptTime
	from #temp1 t1 left outer join #temp2 t2 on t1.data=t2.data
	left outer join #temp3 t3 on t1.data=t3.data
	left outer join #temp4 t4 on t1.data=t4.data
	left outer join #temp5 t5 on t1.data=t5.data	
drop table #temp1,#temp2,#temp3,#temp4,#temp5
--出诊结果统计
select do.NameM resultName,COUNT(*) times,'' rate 
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_AcceptDescript a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码
	left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	left outer join AuSp120.tb_DOutCome do on do.Code=pc.转归编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and  受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by do.NameM
--分诊他院拒绝原因统计
select dr.NameM reason,COUNT(*) times,'' rate from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码
	left outer join AuSp120.tb_DTriageRefuse dr on dr.Code=a.拒绝分诊调度原因编码
	where e.事件性质编码=1 and a.类型编码 in (11,12,13) and a.拒绝分诊调度原因编码 is not null
	and  受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by dr.NameM
--医生护士驾驶员出车时间表
select pc.随车医生 name,COUNT(*) helpNumbers,
	SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)<=180 then 1 else 0 end) helpNormalNumbers,
	SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180 then 1 else 0 end) helpLateNumbers	into #temp1
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_Task t on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_AcceptDescript a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码
	left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.事件类型编码=1
	and  受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' and pc.随车医生<>''
	group by pc.随车医生
select pc.随车医生 name,COUNT(*) tranforNumbers,
	SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)<=180 then 1 else 0 end) tranforNormalNumbers,
	SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180 then 1 else 0 end) tranforLateNumbers	into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_Task t on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码
	left outer join AuSp120.tb_AcceptDescript a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码
	left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码
	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.事件类型编码=2
	and  受理时刻 between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' and pc.随车医生<>''
	group by pc.随车医生
select t1.name,t1.helpLateNumbers,t1.helpNormalNumbers,t1.helpNumbers,isnull(t2.tranforLateNumbers,0) tranforLateNumbers,
	isnull(t2.tranforNormalNumbers,0) tranforNormalNumbers,isnull(t2.tranforNumbers,0) tranforNumbers
	from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name
drop table #temp1,#temp2


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
select * from AuSp120.tb_DAcceptDescriptType
select * from AuSp120.tb_RecordPauseReason



	
