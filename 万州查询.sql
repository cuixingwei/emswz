--  ��������ͳ��
select distinct et.NameM �¼����� ,pc.�������,pc.�������,t.�¼�����, ��� into #dis
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_DEventType et on et.Code=e.�¼����ͱ���
	where e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
select �¼����� outCallType,SUM(���) distance into #temp1 from #dis d group by �¼�����
select et.NameM outCallType,COUNT(et.NameM) times,
	SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks,'' takeBackRate
	into #temp2
	from AuSp120.tb_EventV e
	left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on pc.�������=t.������� and pc.�������=t.������� 	
	left outer join AuSp120.tb_DEventType et on et.Code=e.�¼����ͱ���
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by et.NameM
select et.NameM  outCallType,isnull(AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)),0) averageResponseTime,
	isnull(AVG(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) averageTime,SUM(case when pc.ת�����=7 then 1 else 0 end) refuseToHospitals,
	SUM(case when pc.���ν������ in (2,6,7) then 1 else 0 end) deaths,
	SUM(case when t.������� in (2,3) then 1 else 0 end) emptyCars into #temp3
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DEventType et on et.Code=e.�¼����ͱ���
	left outer join AuSp120.tb_PatientCase pc on pc.�������=t.�¼����� and t.�������=pc.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by et.NameM
select t2.outCallType,isnull(t1.distance,0) distance,t3.emptyCars,t2.takeBackRate,t2.takeBacks,t2.times,t3.averageResponseTime,t3.averageTime,
	t3.deaths,t3.refuseToHospitals 
	from #temp2 t2	
	left outer join #temp3 t3 on t3.outCallType=t2.outCallType
	left outer join #temp1 t1 on t1.outCallType=t2.outCallType
drop table #temp1,#temp2,#temp3,#dis
--����δ����
select  hr.NameM reason,COUNT(*) times,'' rate
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on t.�������=a.������� and t.�¼�����=a.�¼�����
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_DHangReason hr on hr.Code=a.����ԭ�����
	where e.�¼����ʱ���=1 and a.���ͱ��� in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by hr.NameM
--����ʱ��ͳ��
select 	m.���� dispatcher,(select avg(datediff(Second,tr.����ʱ��,tr.ͨ����ʼʱ��)) from AuSp120.tb_TeleRecord tr where  m.����=tr.����Ա���� group by m.����) averageOffhookTime,avg(datediff(Second, a.��ʼ����ʱ��, a.�ɳ�ʱ��)) averageOffSendCar,
	avg(datediff(Second, a.��ʼ����ʱ��, a.��������ʱ��)) averageAccept,(select avg(datediff(Second,sl.��ʼʱ��,sl.����ʱ��)) from AuSp120.tb_SlinoLog sl where sl.��ϯ״̬='����' and m.����=sl.����Ա���� group by m.����) readyTime,
	(select avg(datediff(Second,sl.��ʼʱ��,sl.����ʱ��)) from AuSp120.tb_SlinoLog sl where sl.��ϯ״̬='��ϯ' and m.����=sl.����Ա���� group by m.����) leaveTime
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_Event e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_MrUser m on m.����=t.����Ա����
	where e.�¼����ʱ���=1 
	group by m.����,m.����
--����ҽԺ���ȷ���
select a.�������ҽԺ station,SUM(a.��������) takeBacks into #temp1
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� in (11,12) and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	and a.�ܾ��������ԭ����� is null
	group by a.�������ҽԺ
select a.�������ҽԺ station,SUM(case when a.�ܾ��������ԭ����� is  null then 1 else 0 end) acceptTimes,
	SUM(case when a.�ܾ��������ԭ����� is not null then 1 else 0 end) noAcceptTimes	into #temp2
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� in (11,12) and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by a.�������ҽԺ
select t2.station,t2.acceptTimes,t2.noAcceptTimes,isnull(t1.takeBacks,0) takeBacks
	from #temp2 t2 left outer join #temp1 t1 on t1.station=t2.station
drop table #temp1,#temp2
--ͻ���¼�ͳ��
select gat.NameM eventType,COUNT(gat.NameM) times  into #temp1
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.�¹��������
	where e.�¼����ʱ���=1 and e.�¹��������<>0 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by gat.NameM
select gat.NameM  eventType,COUNT(*) casualties,SUM(case when pc.�������=3 then 1 else 0 end) light,
	SUM(case when pc.�������=2 then 1 else 0 end) middle,SUM(case when pc.�������=5 then 1 else 0 end) heavy,
	SUM(case when pc.���ν������ in (2,6,7) then 1 else 0 end) death  into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.�¹��������
	where e.�¼����ʱ���=1 and e.�¹��������<>0 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by gat.NameM
select distinct gat.NameM  eventType,pc.���,e.����ʱ��,t.�����ֳ�ʱ��,t.����ʱ��,t.����ҽԺʱ��,t.�������,t.������� into #temp3
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.�¹��������
	where e.�¼����ʱ���=1 and e.�¹��������<>0  and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select  t3.eventType,SUM(t3.���) distance,AVG(DATEDIFF(Second,t3.����ʱ��,t3.�����ֳ�ʱ��)) responseTime,
	SUM(DATEDIFF(Second,t3.����ʱ��,t3.����ҽԺʱ��)) timeTotal into #temp4
	from #temp3 t3 
	group by t3.eventType
select t1.eventType,t1.times,isnull(t2.casualties,0) casualties,isnull(t2.death,0) death,isnull(t2.heavy,0) heavy,
	isnull(t2.light,0) light,isnull(t2.middle,0) middle,isnull(t4.distance,0) distance,isnull(t4.responseTime,0) responseTime,isnull(t4.timeTotal,0) timeTotal
	from #temp1 t1 left outer join #temp2 t2 on t1.eventType=t2.eventType
	left outer join #temp4 t4  on t1.eventType=t4.eventType
drop table #temp1,#temp2,#temp3,#temp4

--���ĽӾ�ͳ��
select distinct e.�¼����� eventCode,s.��վ���� station,m.���� dispatcher into #temp1	from AuSp120.tb_EventV e	
	left outer join AuSp120.tb_MrUser m on m.����=e.����Ա����	left outer join AuSp120.tb_Task t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on s.��վ����=t.��վ���� 	where e.�¼����ʱ���=1 and m.��Ա����=0	
select a.ID id,convert(varchar(20),a.��ʼ����ʱ��,120) answerAlarmTime,	a.���ȵ绰 alarmPhone,a.��ϵ�绰 relatedPhone,
	a.�ֳ���ַ siteAddress,	a.�����ж� judgementOnPhone, station,	convert(varchar(20),a.�ɳ�ʱ��,120) sendCarTime, dispatcher	
	from #temp1 t	left outer join AuSp120.tb_AcceptDescriptV a on t.eventCode=a.�¼�����		
	where a.��ʼ����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
drop table #temp1
--˾������ͳ��
select t.˾�� name, COUNT(*) outCalls,
	SUM(case when t.�������=4 then 1 else 0 end) takeBacks,SUM(case when t.������� in (2,3) then 1 else 0 end) emptyCars,
	SUM(case when pc.ת�����=7 then 1 else 0 end) refuseHospitals,	
	SUM(case when pc.���ν������=2 then 1 else 0 end) spotDeaths,
	SUM(case when pc.���ν������ in (6,7) then 1 else 0 end) afterDeaths,	
	SUM(case when e.�¼����ͱ���=3 then 1 else 0 end) inHospitalTransports,
	SUM(case when e.�¼����ͱ���=10 then 1 else 0 end) others,	
	SUM(case when pc.������� is not null then 1 else 0 end) cureNumbers into #temp1	
	from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������	
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����	
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����	
	where e.�¼����ʱ���=1 and t.˾�� is not null and t.˾��<>'' and e.����ʱ��  between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'	
	group by t.˾��    
select distinct t.˾�� name,pc.�������,pc.�������,pc.���,cr.�շѽ��,	
	e.����ʱ��,t.����ҽԺʱ��,t.�����ֳ�ʱ��,t.����ʱ�� into #temp2	from AuSp120.tb_TaskV  t	
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������	
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����	
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����	
	left outer join AuSp120.tb_ChargeRecord cr on t.�������=cr.������� and cr.������ʶ=t.������ʶ  	
	where e.�¼����ʱ���=1 and t.˾�� is not null and t.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'	  
select t2.name,SUM(t2.���) distanceTotal,SUM(t2.�շѽ��) costToal,	
	AVG(DATEDIFF(Second,t2.����ʱ��,t2.�����ֳ�ʱ��)) averageResponseTime,	
	sum(datediff(Second,t2.����ʱ��,t2.����ҽԺʱ��)) outCallTimeTotal into #temp3	from #temp2 t2	group by t2.name  
select t1.name,t1.afterDeaths,t1.cureNumbers,t1.emptyCars,t1.inHospitalTransports,t1.takeBacks,t1.others,
	t1.outCalls,t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,
	isnull(t3.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal	from #temp1 t1 
	left outer join #temp3 t3 on t1.name=t3.name  
	drop table #temp1,#temp2,#temp3 
--ҽ����ʿ˾������ͳ��
select s.��վ���� station,pc.�泵ҽ�� name, COUNT(*) outCalls,SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks,
	SUM(case when t.������� in (2,3) then 1 else 0 end) emptyCars,SUM(case when pc.ת�����=7 then 1 else 0 end) refuseHospitals,
	SUM(case when pc.���ν������=2 then 1 else 0 end) spotDeaths,SUM(case when pc.���ν������ in (6,7) then 1 else 0 end) afterDeaths,
	SUM(case when e.�¼����ͱ���=3 then 1 else 0 end) inHospitalTransports,SUM(case when e.�¼����ͱ���=10 then 1 else 0 end) others,
	SUM(case when pc.������� is not null then 1 else 0 end) cureNumbers into #temp1
	from AuSp120.tb_TaskV  t 
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����
	where e.�¼����ʱ���=1 and pc.�泵ҽ�� is not null and pc.�泵ҽ��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����,pc.�泵ҽ�� 	
select distinct s.��վ���� station,pc.�泵ҽ�� name,pc.�������,pc.�������,pc.���,cr.�շѽ��,
	e.����ʱ��,t.����ҽԺʱ��,t.�����ֳ�ʱ��,t.����ʱ�� into #temp2
	from AuSp120.tb_TaskV  t 
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����
	left outer join AuSp120.tb_ChargeRecord cr on t.�������=cr.������� and cr.������ʶ=t.������ʶ
	where e.�¼����ʱ���=1 and pc.�泵ҽ�� is not null and pc.�泵ҽ��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select t2.station,t2.name,SUM(t2.���) distanceTotal,SUM(t2.�շѽ��) costToal,
	AVG(DATEDIFF(Second,t2.����ʱ��,t2.�����ֳ�ʱ��)) averageResponseTime,
	sum(datediff(Second,t2.����ʱ��,t2.����ҽԺʱ��)) outCallTimeTotal into #temp3
	from #temp2 t2
	group by t2.station,t2.name
select t1.station,t1.name,t1.afterDeaths,t1.cureNumbers,t1.emptyCars,t1.inHospitalTransports,t1.takeBacks,
	t1.others,t1.outCalls,t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,
	isnull(t3.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal
	from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station
drop table #temp1,#temp2,#temp3
--˾�������
select t.˾�� name,COUNT(*) outCalls,SUM(case when t.�������=4 then 1 else 0 end) takeBacks,
	AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) averageResponseTime,SUM(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) outCallTimeTotal,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) averageTime into #temp1
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and t.˾�� is not null and t.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
	group by t.˾��
select distinct t.˾�� ,pc.�������,pc.�������,t.�¼�����, ��� into #dis
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and t.˾�� is not null and t.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
select ˾�� name,SUM(���) distance into #temp2 from #dis d group by ˾��
select t1.name,isnull(t1.averageResponseTime,0) averageResponseTime,isnull(t1.averageTime,0) averageTime,isnull(t1.outCallTimeTotal,0) outCallTimeTotal,
	t1.outCalls,t1.takeBacks,isnull(t2.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name
drop table #dis,#temp1,#temp2
--վ��������
select s.��վ���� station,SUM(case when e.�¼����ͱ���=1 then 1 else 0 end) spotFirstAid,
	SUM(case when e.�¼����ͱ���=2 then 1 else 0 end) stationTransfer,SUM(case when e.�¼����ͱ���=3 then 1 else 0 end) inHospitalTransfer,
	SUM(case when e.�¼����ͱ���=4 then 1 else 0 end) sendOutPatient,SUM(case when e.�¼����ͱ���=5 then 1 else 0 end) safeguard,
	SUM(case when e.�¼����ͱ���=6 then 1 else 0 end) auv,SUM(case when e.�¼����ͱ���=7 then 1 else 0 end) volunteer,
	SUM(case when e.�¼����ͱ���=8 then 1 else 0 end) train,SUM(case when e.�¼����ͱ���=9 then 1 else 0 end) practice,
	SUM(case when e.�¼����ͱ���=10 then 1 else 0 end) other,SUM(case when t.������� is not null then 1 else 0 end) outCallTotal,
	SUM(case when t.�������=4 then 1 else 0 end) tackBackTotal,SUM(case when t.������� in (2,3) then 1 else 0 end) emptyCars,
	SUM(case when t.�������=5 then 1 else 0 end) refuses into #temp1
	from AuSp120.tb_EventV e 
	left outer join  AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join  AuSp120.tb_Station s on s.��վ����=t.��վ����
	where e.�¼����ʱ���=1	and t.��վ���� is not null and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����
select s.��վ���� station,SUM(pc.���) distance,SUM(case when pc.���ν������ in (2,6,7) then 1 else 0 end) death into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.�������=t.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on s.��վ����=pc.��վ���� 
	where e.�¼����ʱ���=1	and pc.��վ���� is not null and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����
select t1.station,t1.spotFirstAid,t1.stationTransfer,t1.inHospitalTransfer,t1.sendOutPatient,t1.safeguard,
	t1.auv,t1.volunteer,t1.train,t1.practice,t1.other,t1.outCallTotal,t1.tackBackTotal,t1.emptyCars,
	t1.refuses,isnull(t2.death,0) death,isnull(t2.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
drop table #temp1,#temp2
--վ����������ͳ��
select distinct pc.�������,pc.�������,pc.��� into #pc from AuSp120.tb_PatientCase pc
select s.��վ���� station,COUNT(*) ztimes,AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) zaverageResponseTime,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) zaverageTime,SUM(pc.���) zdistance into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and t.��վ���� is not null and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����
select s.��վ���� station,COUNT(*) xctimes,AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) xcaverageResponseTime,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) xcaverageTime,SUM(pc.���) xcdistance into #temp2
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and t.��վ���� is not null and e.�¼����ͱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����
select s.��վ���� station,COUNT(*) yytimes,AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) yyaverageResponseTime,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) yyaverageTime,SUM(pc.���) yydistance into #temp3
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and t.��վ���� is not null and e.�¼����ͱ���=2 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����
select t1.station,isnull(t1.zaverageResponseTime,0) zaverageResponseTime,
	isnull(t1.zaverageTime,0) zaverageTime,isnull(t1.zdistance,0) zdistance,isnull(t1.ztimes,0) ztimes,isnull(t2.xcaverageResponseTime,0) xcaverageResponseTime,
	isnull(t2.xcaverageTime,0) xcaverageTime,isnull(t2.xcdistance,0) xcdistance,
	isnull(t2.xctimes,0) xctimes,isnull(t3.yyaverageResponseTime,0) yyaverageResponseTime,
	isnull(t3.yyaverageTime,0) yyaverageTime,isnull(t3.yydistance,0) yydistance,isnull(t3.yytimes,0) yytimes 
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
	left outer join #temp3 t3 on t1.station=t3.station
drop table #pc,#temp1,#temp2,#temp3
--ҽԺת��ͳ��
select distinct pc.�������,pc.�������,pc.���,pc.�����ص� into #pc from AuSp120.tb_PatientCase pc
select da.NameM area,pc.�����ص� station,COUNT(*) outCalls,isnull(SUM(case when t.�������=4 then 1 else 0 end),0) takeBacks,
	isnull(SUM(pc.���),0) distance,isnull(sum(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) time
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	left outer join #pc pc on t.�������=pc.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DArea da on da.Code=a.�������
	where e.�¼����ʱ���=1 and t.��վ���� is not null and e.�¼����ͱ���=2 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM,pc.�����ص�
drop table #pc
--ҽԺת����ϸ
select CONVERT(varchar(20),e.����ʱ��,120) dates,pc.���� patientName,pc.���� age,pc.�Ա� gender,pc.ҽ����� diagnose,pc.�����ַ outCallAddress,pc.���� sendClass 
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	left outer join AuSp120.tb_PatientCase pc  on t.�������=pc.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and t.��վ���� is not null and pc.������� is not null and e.�¼����ͱ���=2 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--Ժ��ת��ͳ��
select distinct pc.�������,pc.�������,pc.���,pc.�����ַ,pc.�����ص� into #pc from AuSp120.tb_PatientCase pc
select s.��վ���� station,COUNT(*) outCalls,isnull(SUM(pc.���),0) distance,isnull(sum(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) time
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	left outer join #pc pc on t.�������=pc.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and t.��վ���� is not null and e.�¼����ͱ���=3 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����
drop table #pc
--Ժ��ת����ϸ
select CONVERT(varchar(20),e.����ʱ��,120) date,pc.���� patientName,pc.���� age,pc.�Ա� gender,pc.ҽ����� diagnose,pc.�����ַ outCallAddress,pc.���� sendClass 
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	left outer join AuSp120.tb_PatientCase pc  on t.�������=pc.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and t.��վ���� is not null and pc.������� is not null and e.�¼����ͱ���=3 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--��Ժ�ڳ�����ϸ
select CONVERT(varchar(20),e.����ʱ��,120) date,pc.���� patientName,pc.���� age,pc.�Ա� gender,pc.ҽ����� diagnose,pc.�����ַ outCallAddress,pc.���� sendClass
	from AuSp120.tb_TaskV t  
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and pc.�������=t.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and t.��վ����='001' and pc.������� is not null and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--������ϸ��ѯ
select cr.�������,cr.�������,cr.�������,SUM(cr.�շѽ��) cost into #temp1 from AuSp120.tb_ChargeRecord cr
	group by cr.�������,cr.�������,cr.�������
select pc.�������,pc.�������,pc.���,pc.���� name,pc.�Ա� gender,pc.���� age,pc.ҽ����� diagnose,pc.�ֳ��ص� address,
	pc.�����ص� toAddress,pc.��� distance into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2015-07-01 00:00:00' and '2015-11-01 00:00:00'
select  t1.cost,t2.age,t2.address,t2.diagnose,t2.distance,t2.gender,t2.name,t2.toAddress 
	from #temp2 t2 left outer join #temp1 t1 on t1.�������=t2.������� and t1.�������=t2.������� and t1.�������=t2.���
drop table #temp1,#temp2
--����ͳ��
select distinct pc.�������,pc.�������,pc.��� into #pc
	from AuSp120.tb_PatientCase pc
select da.NameM area,COUNT(t.�������) outCalls,SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks,isnull(sum(pc.���),0) distance,
	isnull(SUM(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) outCallTime,isnull(AVG(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) averageTime
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DArea da on da.Code=a.�������
	left outer join #pc pc on t.�������=pc.������� and t.�������=pc.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM
drop table #pc
--24Сʱ����ǿ��ͳ��
select  DATEPART(HOUR,e.����ʱ��) span,COUNT(*) times,SUM(case when t.�������=4 then 1 else 0 end) takeBacks,
	isnull(AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)),0) averageResponseTime,isnull(sum(datediff(Second,t.����ʱ��,t.����ҽԺʱ��)),0) outCallTotal,
	isnull(avg(datediff(Second,t.����ʱ��,t.����ҽԺʱ��)),0) averageTime
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by DATEPART(HOUR,e.����ʱ��)
	order by DATEPART(HOUR,e.����ʱ��)
--�����Ʊ�ͳ��
select dc.NameM name,isnull(COUNT(*),0) times,'' rate
	from AuSp120.tb_PatientCase pc  left outer join AuSp120.tb_TaskV t on  t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.�����Ʊ����
	where e.�¼����ʱ���=1 and pc.�����Ʊ���� is not null  and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by dc.NameM
	
--����ͳ��
select dr.NameM name,isnull(COUNT(*),0) times,'' rate
	from AuSp120.tb_PatientCase pc  left outer join AuSp120.tb_TaskV t on  t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DDiseaseReason dr on dr.Code=pc.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by dr.NameM
--�����ص�ͳ��
select pc.�����ص� address, COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	where pc.��¼ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and pc.�����ص� is not null and pc.�����ص�<>''
	group by pc.�����ص�
--���ν��ͳ��
select dr.NameM name, COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_DResult dr on dr.Code=pc.���ν������
	where pc.��¼ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by dr.NameM 
--�����Ա�ͳ��
select pc.�Ա� gender,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc
	where pc.��¼ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by pc.�Ա�
--����ͳ��
select ds.NameM name, COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_DILLState ds  on ds.Code=pc.�������
	where pc.��¼ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by ds.NameM
--ҽ�ƴ���ͳ��
select dm.NameM name,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_CureMeasure cm on cm.�������=pc.������� and cm.�������=pc.������� and cm.�������=pc.���
	left outer join AuSp120.tb_DMeasure dm on dm.Code=cm.���δ�ʩ����
	where pc.��¼ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and dm.NameM is not null
	group by dm.NameM
--ҽ����ʿ˾�����ͳ��
select pc.�泵ҽ�� name,COUNT(*) times, 
	SUM(case when (DATEPART(Hour,t.����ʱ��)<12 and DATEPART(Hour,t.����ҽԺʱ��)>12) or (DATEPART(Hour,t.����ʱ��)<18 and DATEPART(Hour,t.����ҽԺʱ��)>18) then 1 else 0 end)   rate 
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.������� is not null and pc.�泵ҽ��<>''
	group by pc.�泵ҽ��
union 
select pc.�泵��ʿ name,COUNT(*) times, 
	SUM(case when (DATEPART(Hour,t.����ʱ��)<12 and DATEPART(Hour,t.����ҽԺʱ��)>12) or (DATEPART(Hour,t.����ʱ��)<18 and DATEPART(Hour,t.����ҽԺʱ��)>18) then 1 else 0 end)   rate 
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.������� is not null and pc.�泵��ʿ<>''
	group by pc.�泵��ʿ
union
select pc.˾�� name,COUNT(*) times, 
	SUM(case when (DATEPART(Hour,t.����ʱ��)<12 and DATEPART(Hour,t.����ҽԺʱ��)>12) or (DATEPART(Hour,t.����ʱ��)<18 and DATEPART(Hour,t.����ҽԺʱ��)>18) then 1 else 0 end)   rate 
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.������� is not null and pc.˾��<>''
	group by pc.˾��
--������Ժ�ܾ�����ԭ��ͳ��
select a.�������ҽԺ station,COUNT(*) totals into #temp1
	from AuSp120.tb_AcceptDescriptV a left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_DTriageRefuse dtr on dtr.Code=a.�ܾ��������ԭ�����
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and a.���ͱ��� in (11,12)
	group by a.�������ҽԺ
	order by a.�������ҽԺ
select a.�������ҽԺ station,dtr.NameM reason,SUM(case when a.�ܾ��������ԭ����� is not null then 1 else 0 end) times into #temp2
	from AuSp120.tb_AcceptDescriptV a left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_DTriageRefuse dtr on dtr.Code=a.�ܾ��������ԭ�����
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and a.���ͱ��� in (11,12) and dtr.NameM is not null
	group by a.�������ҽԺ,dtr.NameM
	order by a.�������ҽԺ,dtr.NameM
select t1.station,t1.totals,t2.reason,t2.times
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
drop table #temp1,#temp2
--�Ǽ����ó�ͳ��
select distinct pc.�������,pc.�������,pc.��� into #pc from AuSp120.tb_PatientCase pc 
select 'ids' ids, COUNT(*) hospital_times,sum(pc.���) hospital_distance into #temp1
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=3
select 'ids' ids, COUNT(*) safeguard_times,sum(pc.���) safeguard_distance into #temp2
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=5
select 'ids' ids,COUNT(*) clinic_times,sum(pc.���) clinic_distance into #temp3
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=7
select 'ids' ids,COUNT(*) practice_times,sum(pc.���) practice_distance into #temp4
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ��� in (8,9)
select 'ids' ids,COUNT(*) inHospital_times,sum(pc.���) inHospital_distance into #temp5
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=3 and t.��վ����='001'
select 'ids' ids,COUNT(*) xh_times,sum(pc.���) xh_distance into #temp6
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=6
select 'ids' ids,COUNT(*) other_times,sum(pc.���) other_distance into #temp7
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=10
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
--���ȱ�����ϸ
select distinct pc.�������,pc.�������,pc.���,pc.�泵ҽ��,pc.�泵��ʿ,pc.˾�� into #pc from AuSp120.tb_PatientCase pc
select CONVERT(varchar(20),e.����ʱ��,120) date,det.NameM nature,e.�¼����� event,a.�ֳ���ַ address,
	isnull(pc.���,0) distance,pc.�泵ҽ�� doctor,pc.�泵��ʿ nurse,pc.˾�� driver 
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DEventType det on det.Code=e.�¼����ͱ���
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ��� in (1,5)
drop table #pc
--���������ͳ��
select pc.���� span,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	where pc.��¼ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and ISNUMERIC(pc.����)=0 group by pc.����
union
select case when CAST(pc.���� as float) between 0 and 10 then '0~10��' 
	when CAST(pc.���� as float) between 10 and 20 then '10~20��'
	when CAST(pc.���� as float) between 20 and 30 then '20~30��'
	when CAST(pc.���� as float) between 30 and 40 then '30~40��'
	when CAST(pc.���� as float) between 40 and 50 then '40~50��'
	when CAST(pc.���� as float) between 50 and 60 then '50~60��'
	when CAST(pc.���� as float) between 60 and 70 then '60~70��'
	when CAST(pc.���� as float) between 70 and 80 then '70~80��'
	when CAST(pc.���� as float) between 80 and 90 then '80~90��'
	else '90~' end span,COUNT(*) times,'' rate
	from AuSp120.tb_PatientCase pc 
	where  ISNUMERIC(pc.����)=1 and CAST(pc.���� as float)>10 
	and pc.��¼ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by case when CAST(pc.���� as float) between 0 and 10 then '0~10��' 
	when CAST(pc.���� as float) between 10 and 20 then '10~20��'
	when CAST(pc.���� as float) between 20 and 30 then '20~30��'
	when CAST(pc.���� as float) between 30 and 40 then '30~40��'
	when CAST(pc.���� as float) between 40 and 50 then '40~50��'
	when CAST(pc.���� as float) between 50 and 60 then '50~60��'
	when CAST(pc.���� as float) between 60 and 70 then '60~70��'
	when CAST(pc.���� as float) between 70 and 80 then '70~80��'
	when CAST(pc.���� as float) between 80 and 90 then '80~90��'
	else '90~' end
--����Ա����ͳ��
select m.����,COUNT(*) �绰����,sum(case when tr.��¼���ͱ��� in(1,2,3,5,8) then 1 else 0 end) ����,
	sum(case when tr.��¼���ͱ���=6 then 1 else 0 end) ���� into #temp1
	from AuSp120.tb_TeleRecord tr left outer join AuSp120.tb_DTeleRecordType drt on drt.Code=tr.��¼���ͱ���
	left outer join AuSp120.tb_MrUser m on m.����=tr.����Ա���� 
	where not (tr.����Ա����='' or tr.����Ա���� is null ) and  m.��Ա����=0 and tr.��¼���ͱ��� in(1,2,3,5,6,8) and tr.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by m.����	
select m.����,a.���ͱ���,a.��������,a.����Ա����,t.�������,a.��ʼ����ʱ��,t.�¼�����,a.�ɳ�ʱ��,t.�������,t.������� into #temp2 
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�������=t.������� and a.�¼�����=t.�¼�����
	left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_MrUser m on m.����=a.����Ա����
	where e.�¼����ʱ���=1 and m.��Ա����=0 and a.��ʼ����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select t2.����,SUM(t2.��������) ������ into #temp5 from #temp2 t2 where t2.���ͱ��� in (11,12) group by t2.����
select t2.����,SUM(case when t2.��ʼ����ʱ�� is not null and t2.�ɳ�ʱ�� is not null then 1 else 0 end) ��Ч�ɳ�,
	SUM(case when t2.�������=4 then 1 else 0 end)�������,	SUM(case when t2.�������=3 then 1 else 0 end) �ճ�,
	SUM(case when t2.�������=2 then 1 else 0 end) ��ֹ����,SUM(case when t2.�������=5 then 1 else 0 end) �ܾ����� into #temp3 
	from #temp2 t2 group by t2.����
select t2.����,SUM(case when pc.ת�����=1 then 1 else 0 end) �������� into #temp4 from  AuSp120.tb_PatientCase pc
	left outer join #temp2 t2  on pc.�������=t2.������� and t2.�������=pc.������� group by t2.����
select t1.���� dispatcher,t1.�绰���� numbersOfPhone,t1.���� inOfPhone,t1.���� outOfPhone,isnull(t3.��Ч�ɳ�,0) numbersOfSendCar,
	isnull(t3.�������,0) numbersOfNormalSendCar,isnull(t3.�ճ�,0) emptyCar,isnull(t3.��ֹ����,0) numbersOfStopTask,
	isnull(t3.�ܾ�����,0) refuseCar,isnull(t4.��������,0) takeBacks,isnull(t5.������,0) triageNumber from #temp1 t1 
	left outer join #temp3 t3 on t1.����=t3.���� left outer join #temp4 t4 on t1.����=t4.���� left outer join #temp5 t5 on t5.����=t1.����
drop table #temp1,#temp2,#temp3,#temp4,#temp5
--ҽ����ʿ������ϸ




select * from AuSp120.tb_DTeleRecordResult
select * from AuSp120.tb_TeleRecord
select * from AuSp120.tb_DTeleRecordType
select * from AuSp120.tb_DTaskResult ac
select * from AuSp120.tb_AcceptDescript a where a.���ͱ��� in (11,12)
select * from AuSp120.tb_TaskV t where t.�¼�����='2015061510551801'
select * from AuSp120.tb_DILLState
select * from AuSp120.tb_DTriageRefuse
select * from AuSp120.tb_PatientCase pc 
select * from AuSp120.tb_EventV e where e.�¹��������<>0
select * from AuSp120.tb_DOutCome
<<<<<<< HEAD
select * from AuSp120.tb_DArea
=======
select * from AuSp120.tb_DAcceptDescriptType
>>>>>>> 0868d3db9dc932e6ec2d8d03f0b31e6d22eb3264
select * from AuSp120.tb_MrUser




	
