--  ��������ͳ��
select distinct et.NameM �¼����� ,pc.�������,pc.�������,t.�¼�����, ���,a.������� into #dis
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on pc.�������=t.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DEventType et on et.Code=e.�¼����ͱ���
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
select �¼����� outCallType,SUM(���) distance,COUNT(�¼�����) times,SUM(case when �������=1 then 1 else 0 end) shiqu,
	SUM(case when �������=2 then 1 else 0 end) wanzhou,SUM(case when ������� not in (1,2) then 1 else 0 end) others into #temp1 from #dis d group by �¼�����
select et.NameM  outCallType,isnull(AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)),0) averageResponseTime,
	isnull(AVG(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) averageTime,SUM(case when pc.ת�����=7 then 1 else 0 end) refuseToHospitals,
	SUM(case when pc.ת����� in (5,6) then 1 else 0 end) deaths,SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks,COUNT(pc.ת�����) takeBackRate,
	SUM(case when pc.ת�����=10 then 1 else 0 end) emptyCars into #temp3
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on pc.�������=t.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DEventType et on et.Code=e.�¼����ͱ���
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	group by et.NameM
select t1.outCallType,isnull(t1.distance,0) distance,t3.emptyCars,t3.takeBackRate,t3.takeBacks,t1.times,t3.averageResponseTime,t3.averageTime,
	t3.deaths,t3.refuseToHospitals,ISNULL(t1.shiqu,0) shiqu,isnull(t1.wanzhou,0) wanzhou,ISNULL(t1.others,0) others
	from #temp3 t3	
	left outer join #temp1 t1 on t3.outCallType=t1.outCallType
drop table #temp1,#temp3,#dis
--����δ����
select hr.NameM reason,COUNT(*) times,'' rate
	from AuSp120.tb_EventV e  left outer join AuSp120.tb_AcceptDescriptV a on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_TaskV t on t.�������=a.������� and t.�¼�����=a.�¼�����
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
	where e.�¼����ʱ���=1 and a.���ͱ��� in (11,12,13) and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
	and a.�ܾ��������ԭ����� is null
	group by a.�������ҽԺ
select a.�������ҽԺ station,SUM(case when a.�ܾ��������ԭ����� is  null then 1 else 0 end) acceptTimes,
	SUM(case when a.�ܾ��������ԭ����� is not null then 1 else 0 end) noAcceptTimes	into #temp2
	from AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� in (11,12,13) and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'
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
select t.�¼�����,t.�������,dr.NameM outResult,pc.�����ַ,SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks into #temp1
	from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_Task t on pc.�������=t.������� and pc.�������=t.������� 
	left outer join AuSp120.tb_DResult dr on pc.���ν������=dr.Code
	group by t.�¼�����,dr.NameM,pc.�����ַ,t.�������
select convert(varchar(20),e.����ʱ��,120) answerAlarmTime,m.���� dispatcher,a.���ȵ绰 alarmPhone,a.��ϵ�绰 relatedPhone,a.�������ҽԺ triageStation,
	a.�ֳ���ַ siteAddress,	a.�����ж� judgementOnPhone,t1.�����ַ station,t1.outResult,t1.takeBacks,convert(varchar(20),a.�ɳ�ʱ��,120) sendCarTime
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on e.�¼�����=a.�¼�����
	left outer join #temp1 t1 on t1.�¼�����=a.�¼����� and t1.�������=a.�������
	left outer join AuSp120.tb_MrUser m on m.����=a.����Ա����
	where e.�¼����ʱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
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
select distinct �������,�������,������� into #cm from AuSp120.tb_CureMeasure
select s.��վ���� station,pc.˾�� name,SUM(case when cm.ID is not null then 1 else 0 end) cureNumbers  into #temp6
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_CureMeasure cm on cm.�������=pc.������� and cm.�������=pc.������� and cm.�������=pc.���
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.˾�� is not null and pc.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����,pc.˾�� 
select s.��վ���� station,pc.˾�� name, SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks,
	SUM(case when pc.ת�����=10 then 1 else 0 end) emptyCars,SUM(case when pc.ת�����=7 then 1 else 0 end) refuseHospitals,
	SUM(case when pc.ת�����=5 then 1 else 0 end) spotDeaths,SUM(case when pc.ת�����=6 then 1 else 0 end) afterDeaths,
	SUM(case when pc.ת�����=8 then 1 else 0 end) others,SUM(case when pc.ת�����=12 then 1 else 0 end) safeOut,
	SUM(case when pc.ת�����=11 then 1 else 0 end) noAmbulance into #temp1
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	left outer join #cm cm on cm.�������=pc.������� and cm.�������=pc.������� and cm.�������=pc.���
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.˾�� is not null and pc.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by s.��վ����,pc.˾�� 	
select distinct s.��վ���� station,pc.˾�� name,pc.�������,pc.�������,pc.���,cr.�շѽ��,t.��������ʱ��,
	e.����ʱ��,t.����ҽԺʱ��,t.�����ֳ�ʱ��,t.����ʱ�� into #temp2
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����
	left outer join AuSp120.tb_ChargeRecord cr on t.�������=cr.������� and cr.������ʶ=t.������ʶ
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.˾�� is not null and pc.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select distinct s.��վ���� station,pc.˾�� name,pc.���,t.��������ʱ��,
	e.����ʱ��,t.����ҽԺʱ��,t.�����ֳ�ʱ��,t.����ʱ�� into #temp4
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_Station s on  s.��վ����=t.��վ����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.˾�� is not null and pc.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
select t2.station,t2.name,SUM(t2.�շѽ��) costToal into #temp5
	from #temp2 t2
	group by t2.station,t2.name
select t4.station,t4.name,SUM(t4.���) distanceTotal,COUNT(*) outCalls,
	AVG(DATEDIFF(Second,t4.����ʱ��,t4.�����ֳ�ʱ��)) averageResponseTime,
	AVG(DATEDIFF(Second,t4.��������ʱ��,t4.����ʱ��)) averageSendTime,
	sum(datediff(Second,t4.����ʱ��,t4.����ҽԺʱ��)) outCallTimeTotal into #temp3
	from #temp4 t4
	group by t4.station,t4.name
select t1.station,t1.name,t1.afterDeaths,t6.cureNumbers,t1.emptyCars,t1.safeOut,t1.takeBacks,t1.noAmbulance,t1.others,t3.outCalls,
	t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,isnull(t3.averageSendTime,0) averageSendTime,
	isnull(t5.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal
	from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station
	left outer join #temp5 t5 on t1.name=t5.name and t1.station=t5.station 
	left outer join #temp6 t6 on t1.name=t6.name and t1.station=t6.station order by t1.station
drop table #temp1,#temp2,#temp3,#temp4,#temp5,#cm,#temp6
--˾�������
select distinct pc.˾�� ,pc.�������,pc.�������, ��� into #pc from AuSp120.tb_PatientCase pc
select pc.˾�� name,COUNT(*) outCalls,	AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) averageResponseTime,
	SUM(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) outCallTimeTotal,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) averageTime,SUM(���) distance  into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼�����
	left outer join	AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join #pc pc on t.�������=pc.������� and t.�������=pc.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.˾�� is not null and pc.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
	group by pc.˾��
select pc.˾�� name,SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks into #temp2 from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on a.�¼�����=e.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.˾�� is not null and pc.˾��<>'' and e.����ʱ�� between '2014-01-01 00:00:00' and '2016-05-01 00:00:00'	
	group by pc.˾��
select t1.name,isnull(t1.averageResponseTime,0) averageResponseTime,isnull(t1.averageTime,0) averageTime,isnull(t1.outCallTimeTotal,0) outCallTimeTotal,
	t1.outCalls,t2.takeBacks,isnull(t1.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name
drop table #pc,#temp1,#temp2
--վ��������
select distinct pc.�������,pc.�������,pc.�����ַ,pc.��� into #pc from AuSp120.tb_PatientCase pc
select pc.�����ַ station,SUM(case when e.�¼����ͱ���=1 then 1 else 0 end) spotFirstAid,SUM(pc.���) distance,
	SUM(case when e.�¼����ͱ���=2 then 1 else 0 end) stationTransfer,SUM(case when e.�¼����ͱ���=3 then 1 else 0 end) inHospitalTransfer,
	SUM(case when e.�¼����ͱ��� in (12,13) then 1 else 0 end) sendOutPatient,SUM(case when e.�¼����ͱ���=5 then 1 else 0 end) safeguard,
	SUM(case when e.�¼����ͱ���=6 then 1 else 0 end) auv,SUM(case when e.�¼����ͱ���=7 then 1 else 0 end) volunteer,
	SUM(case when e.�¼����ͱ���=8 then 1 else 0 end) train,SUM(case when e.�¼����ͱ���=9 then 1 else 0 end) practice,
	SUM(case when e.�¼����ͱ���=10 then 1 else 0 end) other,COUNT(*) outCallTotal into #temp1
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_TaskV t on t.�¼�����=a.�¼����� and t.�������=a.�������
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.�����ַ
select pc.�����ַ station,SUM(case when pc.ת����� in (5,6) then 1 else 0 end) death,
	SUM(case when pc.ת�����=1 then 1 else 0 end) tackBackTotal,SUM(case when pc.ת�����=10 then 1 else 0 end) emptyCars,
	SUM(case when pc.ת�����=7 then 1 else 0 end) refuses into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.�������=t.������� and t.�������=pc.�������
	left outer join AuSp120.tb_AcceptDescriptV a on t.�¼�����=a.�¼����� and t.�������=a.�������
	left outer join AuSp120.tb_EventV e on a.�¼�����=e.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4)	and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.�����ַ
select t1.station,t1.spotFirstAid,t1.stationTransfer,t1.inHospitalTransfer,t1.sendOutPatient,t1.safeguard,
	t1.auv,t1.volunteer,t1.train,t1.practice,t1.other,t1.outCallTotal,t2.tackBackTotal,t2.emptyCars,
	t2.refuses,isnull(t2.death,0) death,isnull(t1.distance,0) distance
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
	where t1.station is not null and t1.station<>''
drop table #temp1,#temp2,#pc
--վ����������ͳ��
select distinct pc.�������,pc.�������,pc.���,pc.�����ַ into #pc from AuSp120.tb_PatientCase pc
select pc.�����ַ  station,COUNT(*) ztimes,AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) zaverageResponseTime,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) zaverageTime,SUM(pc.���) zdistance into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼����� 
	left outer join AuSp120.tb_TaskV t on t.�¼�����=a.�¼����� and t.�������=a.�������
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.�����ַ 
select pc.�����ַ station,COUNT(*) xctimes,AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) xcaverageResponseTime,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) xcaverageTime,SUM(pc.���) xcdistance into #temp2
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼����� 
	left outer join AuSp120.tb_TaskV t on t.�¼�����=a.�¼����� and t.�������=a.�������
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.�¼����ͱ���=1 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.�����ַ
select pc.�����ַ station,COUNT(*) yytimes,AVG(DATEDIFF(Second,e.����ʱ��,t.�����ֳ�ʱ��)) yyaverageResponseTime,
	avg(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)) yyaverageTime,SUM(pc.���) yydistance into #temp3
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼����� 
	left outer join AuSp120.tb_TaskV t on t.�¼�����=a.�¼����� and t.�������=a.�������
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.�¼����ͱ���=2 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.�����ַ
select t1.station,isnull(t1.zaverageResponseTime,0) zaverageResponseTime,
	isnull(t1.zaverageTime,0) zaverageTime,isnull(t1.zdistance,0) zdistance,isnull(t1.ztimes,0) ztimes,isnull(t2.xcaverageResponseTime,0) xcaverageResponseTime,
	isnull(t2.xcaverageTime,0) xcaverageTime,isnull(t2.xcdistance,0) xcdistance,
	isnull(t2.xctimes,0) xctimes,isnull(t3.yyaverageResponseTime,0) yyaverageResponseTime,
	isnull(t3.yyaverageTime,0) yyaverageTime,isnull(t3.yydistance,0) yydistance,isnull(t3.yytimes,0) yytimes 
	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station
	left outer join #temp3 t3 on t1.station=t3.station  where t1.station is not null and t1.station<>''
drop table #pc,#temp1,#temp2,#temp3
--ҽԺת��ͳ��
select distinct pc.�������,pc.�������,pc.��� into #pc from AuSp120.tb_PatientCase pc
select da.NameM area ,a.�ֳ���ַ station,COUNT(*) outCalls,
	isnull(SUM(pc.���),0) distance,isnull(sum(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) time into #temp1
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join #pc pc on t.�������=pc.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DArea da on da.Code=a.�������
	where e.�¼����ʱ���=1  and a.���ͱ��� not in (2,4)  and e.�¼����ͱ���=2 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM,a.�ֳ���ַ
select da.NameM area ,a.�ֳ���ַ station,SUM(case when pc.ת�����=1 then 1 else 0 end) takeBacks into #temp2
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DArea da on da.Code=a.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4)  and e.�¼����ͱ���=2 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by da.NameM,a.�ֳ���ַ
select t1.area,t1.station,t1.distance,t1.outCalls,t2.takeBacks,t1.time 
	from #temp1 t1 left outer join #temp2 t2 on t1.area=t2.area and t1.station=t2.station order by t1.area
drop table #pc,#temp1,#temp2
--ҽԺת����ϸ
select CONVERT(varchar(20),e.����ʱ��,120) dates,pc.���� patientName,pc.���� age,pc.�Ա� gender,pc.ҽ����� diagnose,
	pc.�����ַ outCallAddress,pc.���� sendClass,a.�ֳ���ַ spotAddress,pc.��� distance,dr.NameM outResult
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_PatientCase pc  on t.�������=pc.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DResult dr on dr.Code=pc.���ν������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4)  and e.�¼����ͱ���=2 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--Ժ��ת��ͳ��
select distinct pc.�������,pc.�������,pc.���,pc.�����ַ into #pc from AuSp120.tb_PatientCase pc
select pc.�����ַ station,COUNT(*) outCalls,isnull(SUM(pc.���),0) distance,isnull(sum(DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��)),0) time
	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join #pc pc on t.�������=pc.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and pc.�����ַ is not null and pc.�����ַ<>'' and a.���ͱ��� not in (2,4) and e.�¼����ͱ���=3 and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by pc.�����ַ
drop table #pc
--Ժ��ת����ϸ
select CONVERT(varchar(20),e.����ʱ��,120) date,pc.���� patientName,pc.���� age,pc.�Ա� gender,
	pc.ҽ����� diagnose,pc.�����ַ outCallAddress,pc.���� sendClass,a.�ֳ���ַ spot,pc.��� distance,
	DATEDIFF(Second,t.����ʱ��,t.����ҽԺʱ��) time
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼�����
	left outer join AuSp120.tb_TaskV t on t.�¼�����=a.�¼����� and t.�������=a.�������
	left outer join AuSp120.tb_PatientCase pc  on t.�������=pc.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4)   
	and e.�¼����ͱ���=3 and pc.������� is not null and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	
--��Ժ�ڳ�����ϸ
select CONVERT(varchar(20),e.����ʱ��,120) date,pc.���� patientName,pc.���� age,pc.�Ա� gender,pc.ҽ����� diagnose,pc.�ֳ��ص� outCallAddress,pc.���� sendClass
	from AuSp120.tb_TaskV t  
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and pc.�������=t.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and t.��վ����='001' and pc.������� is not null and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
--������ϸ��ѯ
select cr.�������,cr.�������,cr.�������,SUM(cr.�շѽ��) cost into #temp1 from AuSp120.tb_ChargeRecord cr
	group by cr.�������,cr.�������,cr.�������
select pc.�������,pc.�������,pc.���,pc.���� name,pc.�Ա� gender,pc.���� age,pc.ҽ����� diagnose,pc.�ֳ��ص� address,
	pc.�����ص� toAddress,pc.��� distance into #temp2
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_PatientCase pc on t.�������=pc.������� and t.�������=pc.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.���ޱ�־=1 and e.����ʱ�� between '2015-07-01 00:00:00' and '2015-11-01 00:00:00'
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
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼�����
	left outer join AuSp120.tb_TaskV t on t.�¼�����=a.�¼����� and t.�������=a.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
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
select distinct �������,�������,�泵ҽ��,�泵��ʿ,˾�� into #pc from AuSp120.tb_PatientCase  
select pc.�泵ҽ�� name,COUNT(*) rate, 
	SUM(case when (DATEPART(Hour,t.����ʱ��)<12 and DATEPART(Hour,t.����ҽԺʱ��)>12) or (DATEPART(Hour,t.����ʱ��)<18 and DATEPART(Hour,t.����ҽԺʱ��)>18) then 1 else 0 end)   times 
	from #pc pc left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.������� is not null and pc.�泵ҽ��<>''
	group by pc.�泵ҽ��
union 
select pc.�泵��ʿ name,COUNT(*) rate, 
	SUM(case when (DATEPART(Hour,t.����ʱ��)<12 and DATEPART(Hour,t.����ҽԺʱ��)>12) or (DATEPART(Hour,t.����ʱ��)<18 and DATEPART(Hour,t.����ҽԺʱ��)>18) then 1 else 0 end)   times 
	from #pc pc left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.������� is not null and pc.�泵��ʿ<>''
	group by pc.�泵��ʿ
union
select pc.˾�� name,COUNT(*) rate, 
	SUM(case when (DATEPART(Hour,t.����ʱ��)<12 and DATEPART(Hour,t.����ҽԺʱ��)>12) or (DATEPART(Hour,t.����ʱ��)<18 and DATEPART(Hour,t.����ҽԺʱ��)>18) then 1 else 0 end)   times 
	from #pc pc left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
	and pc.������� is not null and pc.˾��<>''
	group by pc.˾��
drop table #pc
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
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=3
select 'ids' ids, COUNT(*) safeguard_times,sum(pc.���) safeguard_distance into #temp2
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=5
select 'ids' ids,COUNT(*) clinic_times,sum(pc.���) clinic_distance into #temp3
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=7
select 'ids' ids,COUNT(*) practice_times,sum(pc.���) practice_distance into #temp4
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ��� in (8,9)
select 'ids' ids,COUNT(*) inHospital_times,sum(pc.���) inHospital_distance into #temp5
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=3 and t.��վ����='001'
select 'ids' ids,COUNT(*) xh_times,sum(pc.���) xh_distance into #temp6
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=6
select 'ids' ids,COUNT(*) other_times,sum(pc.���) other_distance into #temp7
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=10
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
select CONVERT(varchar(20),e.����ʱ��,120) date,det.NameM nature,a.�����ж� event,a.�ֳ���ַ address,
	isnull(pc.���,0) distance,pc.�泵ҽ�� doctor,pc.�泵��ʿ nurse,pc.˾�� driver,DATEDIFF(SECOND,t.����ʱ��,t.����ҽԺʱ��) safeTime
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.�������
	left outer join AuSp120.tb_DEventType det on det.Code=e.�¼����ͱ���
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00' and e.�¼����ͱ���=5
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
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and m.��Ա����=0 and a.��ʼ����ʱ�� between '2014-01-01 00:00:00' and '2015-11-01 00:00:00'
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
--��Ͽ����ҽԺ������ϸ��
select convert(varchar(20),e.����ʱ��,120) dateTime,pc.���� patientName,pc.�ֳ��ص� address,pc.�����ַ outStation, 
	dr.NameM outResult,pc.�泵ҽ�� doctor,pc.�泵��ʿ nurse,pc.��� distance,	pc.˾�� driver,pc.�Ա� sex,pc.���� age, 
	pc.ҽ����� diagnose,pc.�����ص� sendAddress,da.NameM area,dc.NameM diseaseDepartment,dcs.NameM classState,dis.NameM diseaseDegree,
	de.NameM treatmentEffet,et.NameM eventType,t.�������� carCode,DATEDIFF(Second,t.��������ʱ��,t.�����ֳ�ʱ��) poorTime,
	DATEDIFF(Second,t.����ʱ��,t.���ʱ��) userTime,AuSp120.GetCureMeasure(pc.�������,pc.���) cureMeasure,u.���� dispatcher
	from AuSp120.tb_PatientCase pc  
	left outer join AuSp120.tb_TaskV t on t.�������=pc.������� and t.�������=pc.�������	 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼����� 	 
	left outer join AuSp120.tb_DResult dr on dr.Code=t.�������	 
	left outer join AuSp120.tb_AcceptDescriptV  a on a.�������=t.������� and a.�¼�����=t.�¼�����	 
	left outer join AuSp120.tb_DArea da on da.Code=a.�������  
	left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.�����Ʊ����	 
	left outer join AuSp120.tb_DDiseaseClassState dcs on dcs.Code=pc.����ͳ�Ʊ���	 
	left outer join AuSp120.tb_DILLState dis on dis.Code=pc.�������	 
	left outer join AuSp120.tb_DEffect de on de.Code=pc.����Ч������
	left outer join AuSp120.tb_MrUser u on u.����=t.����Ա����
	left outer join AuSp120.tb_DEventType et on et.Code=e.�¼����ͱ���	 
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and pc.�����ַ in ('��Ͽ����ҽԺ���ȷ�Ժ','��Ͽ����ҽԺ�ٰ���Ժ','��Ͽ����ҽԺ���Ϸ�Ժ') 
	and e.����ʱ�� between  '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
--�����������ͳ��
select ʵ�ʱ�ʶ carCode,count( p.��������) as pauseNumbers into #temp1 	
	from AuSp120.tb_RecordPauseReason p left join AuSp120.tb_Ambulance a on p.��������=a.�������� 	
	where p.����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' group by (ʵ�ʱ�ʶ) 
select am.ʵ�ʱ�ʶ carCode,t.����ʱ�� ,t.�����ֳ�ʱ��,t.��������ʱ��,������� into #temp2	
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����	
	left outer join AuSp120.tb_Ambulance am on am.��������=t.�������� 	
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and t.��������ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
select carCode,AVG(DATEDIFF(Second,��������ʱ��,����ʱ��)) averageOutCarTimes into #temp3 	
	from #temp2 where ��������ʱ��<����ʱ�� group by carCode	
select carCode,AVG(DATEDIFF(Second,����ʱ��,�����ֳ�ʱ��)) averageArriveSpotTimes into #temp4	
	from #temp2 where ����ʱ��<�����ֳ�ʱ�� and ����ʱ�� is not null group by carCode 
select t.carCode,sum(case when t.����ʱ�� is not null then 1 else 0 end) outCarNumbers,
	sum(case when t.�����ֳ�ʱ�� is not null then 1 else 0 end) arriveSpotNumbers into #temp5	
	from #temp2 t group by t.carCode 
select distinct pc.�������,pc.�������,pc.���,pc.������ʶ into #pc from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.�������=t.������� and pc.�������=t.�������
	where t.��������ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select pc.������ʶ carCode,sum(pc.���) outDistance into #dis1 from #pc pc group by pc.������ʶ
select gv.ʵ�ʱ�ʶ carCode,(MAX(gv.���)-MIN(���)) distance into #dis2 from AuSp120.tb_GPSInfoV gv 
	where gv.ʱ��  between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' and gv.��� <>0  group by gv.ʵ�ʱ�ʶ
select t5.carCode,outCarNumbers,averageOutCarTimes,arriveSpotNumbers,averageArriveSpotTimes,isnull(pauseNumbers,0) pauseNumbers,d1.outDistance,d2.distance
	from  #temp5  t5  	
	left outer join #temp1 t1 on  t1.carCode=t5.carCode	
	left outer join #temp3 t3 on t3.carCode=t5.carCode	
	left outer join #temp4 t4 on t5.carCode=t4.carCode	
	left outer join #dis1 d1 on d1.carCode=t5.carCode
	left outer join #dis2 d2 on d2.carCode=t5.carCode
	where t5.carCode is not null	
drop table #temp1,#temp2,#temp3,#temp4,#temp5,#dis1,#dis2,#pc
--�ſճ�ͳ��
select convert(varchar(20),a.��ʼ����ʱ��,120) acceptTime,a.�ֳ���ַ sickAddress, 
	m.���� dispatcher,	isnull(DATEDIFF(Second,t.����ʱ��,t.;�д���ʱ��),0) emptyRunTimes,der.NameM emptyReason,et.NameM eventType	 
	from AuSp120.tb_AcceptDescriptV a 
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DEventType et on et.Code=e.�¼����ͱ���		 
	left outer join AuSp120.tb_MrUser m on t.����Ա����=m.����	 
	left outer join AuSp120.tb_DEmptyReason der on der.Code=t.�ſճ�ԭ�����  	 
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and t.�������=3 and t.�ſճ�ԭ����� is not null 
	and a.��ʼ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
--�����¼���ˮͳ��
select a.�¼�����,a.�������,e.�¼����� eventName,dat.NameM acceptType,CONVERT(varchar(20),a.��ʼ����ʱ��,120) hungTime,da.NameM area,et.NameM eventType, 
	dhr.NameM hungReason,m.���� dispatcher,CONVERT(varchar(20),a.��������ʱ��,120) endTime,
	ISNULL(DATEDIFF(Second,a.��ʼ����ʱ��,a.��������ʱ��),0) hungtimes,a.�������ҽԺ station into #temp1	 
	from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.�¼�����=e.�¼�����	 
	left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.����ԭ�����	 
	left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.���ͱ���	 
	left outer join AuSp120.tb_DArea da on da.Code=a.�������
	left outer join AuSp120.tb_DEventType et on e.�¼����ͱ���=et.Code
	left outer join AuSp120.tb_MrUser m on m.����=e.����Ա����	where e.�¼����ʱ���=1  
	and a.��ʼ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'  and a.����ԭ����� is not null
select a.�¼�����,a.�������,er.NameM �¼���� into #temp2
	from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.�¼�����=e.�¼�����
	left outer join AuSp120.tb_DEventResult er on er.Code=e.�¼��������
	where e.�¼����ʱ���=1 and a.����ԭ����� is null  and a.��ʼ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'  
	and a.�¼����� in (select a.�¼�����	from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.�¼�����=e.�¼����� where e.�¼����ʱ���=1 and a.����ԭ����� is not null)
select distinct t1.acceptType,t1.area,t1.dispatcher,t1.endTime,t1.eventName,t1.eventType,t1.hungReason,t1.hungTime,
	t1.hungtimes,t1.station,t2.�¼���� result
	from #temp1 t1 left outer join #temp2 t2 on t1.�¼�����=t2.�¼����� and t2.�������>t1.�������
drop table #temp1,#temp2
--����վ3����δ�������ͳ�Ʊ�
select distinct pc.�泵ҽ��,pc.�泵��ʿ,pc.˾��,pc.�������,pc.������� into #pc from AuSp120.tb_PatientCase pc 
select a.�ֳ���ַ siteAddress,det.NameM eventType,am.ʵ�ʱ�ʶ carCode,CONVERT(varchar(20),a.��ʼ����ʱ��,120) acceptTime, 
	CONVERT(varchar(20),t.��������ʱ��,120) acceptTaskTime,	CONVERT(varchar(20),t.����ʱ��,120) outCarTime, 
	DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��) outCarTimes,	dtr.NameM taskResult,t.��ע remark,m.���� dispatcher,pc.˾�� driver,
	pc.�泵ҽ�� docter,pc.�泵��ʿ nurse,s.��վ���� station	 
	from AuSp120.tb_TaskV t	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and t.�������=a.������� 	 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����	
	left outer join #pc pc on pc.�������=t.������� and pc.�������=t.������� 
	left outer join AuSp120.tb_DEventType det on det.Code=e.�¼����ͱ���	 
	left outer join AuSp120.tb_MrUser m on m.����=t.����Ա����	 
	left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.�������	
	left outer join AuSp120.tb_Station s on t.��վ���� =s.��վ����
	left outer join AuSp120.tb_Ambulance am on am.��������=t.��������	
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) 	and t.��������ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
	and DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��)>180
drop table #pc
--����ʱ�����3����ͳ�Ʊ�
select det.NameM outType,SUM(case when DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��)<=180 then 1 else 0 end) normalNumbers,
	'' rate1,SUM(case when DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��)>180 then 1 else 0 end) lateNumbers,'' rate2,
	COUNT(*) total
	from AuSp120.tb_TaskV t	
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and t.�������=a.������� 	 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_DEventType det on det.Code=e.�¼����ͱ���
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) 
	and t.��������ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' 
	group by det.NameM
--����ҵ������ͳ��
select 'data' data,SUM(case when ������Դ����=1 then 1 else 0 end) helpPhone,SUM(case when ������Դ����=2 then 1 else 0 end) phoneOf110,
	SUM(case when ������Դ����=3 then 1 else 0 end) phoneOf119,SUM(case when ������Դ����=4 then 1 else 0 end) phoneOfOther into #temp1
	from AuSp120.tb_Event where �¼����ʱ���=1 and ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,SUM(case when ��¼���ͱ��� in (1,2,3,5,8) then 1 else 0 end) inPhone,
	SUM(case when ��¼���ͱ���=6 then 1 else 0 end) outPhone,COUNT(*) totalPhone into #temp2
	from AuSp120.tb_TeleRecord where ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,SUM(case when e.�¼����ͱ���=1 then 1 else 0 end) spotFirstAid,
	SUM(case when e.�¼����ͱ���=2 then 1 else 0 end) stationTransfer,
	SUM(case when e.�¼����ͱ���=3 then 1 else 0 end) inHospitalTransfer,
	SUM(case when e.�¼����ͱ���=5 then 1 else 0 end) safeguard,
	SUM(case when e.�¼����ͱ��� not in (1,2,3,5) then 1 else 0 end) noFirstAid,
	SUM(case when t.�������=2 then 1 else 0 end) stopStaskNumbers,
	SUM(case when a.���ͱ��� in (2,4) then 1 else 0 end) hungNumbers,
	SUM(case when a.���ͱ��� in (11,12,13) then 1 else 0 end) referralSendCar,
	SUM(case when a.���ͱ��� in (2,4,11,12,13) then 1 else 0 end) centerSendCar into #temp3
	from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescript a on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_TaskV t on a.�������=t.������� and a.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,AVG(DATEDIFF(second,a.��ʼ����ʱ��,a.��������ʱ��)) firstaidAcceptTime into #temp4
	from AuSp120.tb_Event e left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼�����
	where e.�¼����ʱ���=1 and e.�¼����ͱ���=1 and ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
select 'data' data,AVG(DATEDIFF(second,a.��ʼ����ʱ��,a.��������ʱ��))  referralAcceptTime into #temp5
	from AuSp120.tb_Event e left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=e.�¼�����
	where e.�¼����ʱ���=1 and e.�¼����ͱ���=2 and ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
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
--������ͳ��
select do.NameM resultName,COUNT(*) times,'' rate 
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.�������=t.������� and pc.�������=t.�������
	left outer join AuSp120.tb_AcceptDescript a on a.�������=t.������� and a.�¼�����=t.�¼�����
	left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_DOutCome do on do.Code=pc.ת�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and  ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by do.NameM
--������Ժ�ܾ�ԭ��ͳ��
select dr.NameM reason,COUNT(*) times,'' rate from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_AcceptDescriptV a on e.�¼�����=a.�¼�����
	left outer join AuSp120.tb_DTriageRefuse dr on dr.Code=a.�ܾ��������ԭ�����
	where e.�¼����ʱ���=1 and a.���ͱ��� in (11,12,13) and a.�ܾ��������ԭ����� is not null
	and  ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00'
	group by dr.NameM
--ҽ����ʿ��ʻԱ����ʱ���
select pc.�泵ҽ�� name,COUNT(*) helpNumbers,
	SUM(case when DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��)<=180 then 1 else 0 end) helpNormalNumbers,
	SUM(case when DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��)>180 then 1 else 0 end) helpLateNumbers	into #temp1
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_Task t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_AcceptDescript a on a.�������=t.������� and a.�¼�����=t.�¼�����
	left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.�¼����ͱ���=1
	and  ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' and pc.�泵ҽ��<>''
	group by pc.�泵ҽ��
select pc.�泵ҽ�� name,COUNT(*) tranforNumbers,
	SUM(case when DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��)<=180 then 1 else 0 end) tranforNormalNumbers,
	SUM(case when DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��)>180 then 1 else 0 end) tranforLateNumbers	into #temp2
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_Task t on t.�������=pc.������� and t.�������=pc.�������
	left outer join AuSp120.tb_AcceptDescript a on a.�������=t.������� and a.�¼�����=t.�¼�����
	left outer join AuSp120.tb_EventV e on e.�¼�����=a.�¼�����
	where e.�¼����ʱ���=1 and a.���ͱ��� not in (2,4) and e.�¼����ͱ���=2
	and  ����ʱ�� between '2013-01-01 00:00:00' and '2015-11-01 00:00:00' and pc.�泵ҽ��<>''
	group by pc.�泵ҽ��
select t1.name,t1.helpLateNumbers,t1.helpNormalNumbers,t1.helpNumbers,isnull(t2.tranforLateNumbers,0) tranforLateNumbers,
	isnull(t2.tranforNormalNumbers,0) tranforNormalNumbers,isnull(t2.tranforNumbers,0) tranforNumbers
	from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name
drop table #temp1,#temp2


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
select * from AuSp120.tb_DAcceptDescriptType
select * from AuSp120.tb_RecordPauseReason



	
