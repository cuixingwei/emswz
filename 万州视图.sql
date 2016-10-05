if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[tb_AcceptDescriptV]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[tb_AcceptDescriptV]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[tb_EventV]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[tb_EventV]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[tb_GPSInfoV]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[tb_GPSInfoV]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[tb_TaskV]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[tb_TaskV]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[tb_TeleRecordV]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[tb_TeleRecordV]
GO

CREATE VIEW [AuSp120].[tb_AcceptDescriptV]
AS SELECT * FROM AuSp120.tb_AcceptDescriptHis UNION SELECT * FROM AuSp120.tb_AcceptDescript

GO

CREATE VIEW [AuSp120].[tb_EventV]
AS SELECT * FROM AuSp120.tb_EventHis UNION SELECT * FROM AuSp120.tb_Event

GO

CREATE VIEW [AuSp120].[tb_GPSInfoV]
AS SELECT * FROM AuSp120.tb_GPSInfoMask UNION SELECT * FROM AuSp120.tb_GPSInfo

GO

CREATE VIEW [AuSp120].[tb_TaskV]
AS SELECT * FROM AuSp120.tb_TaskHis UNION SELECT * FROM AuSp120.tb_Task

GO

CREATE VIEW [AuSp120].[tb_TeleRecordV]
AS SELECT * FROM AuSp120.tb_TeleRecordHis UNION SELECT * FROM AuSp120.tb_TeleRecord

GO