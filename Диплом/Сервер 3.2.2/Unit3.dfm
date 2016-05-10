object Form3: TForm3
  Left = 178
  Top = 114
  Width = 1152
  Height = 615
  Caption = #1041#1044
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 8
    Top = 8
    Width = 42
    Height = 13
    Caption = #1043#1083#1072#1074#1085#1072#1103
  end
  object Label2: TLabel
    Left = 8
    Top = 200
    Width = 76
    Height = 13
    Caption = #1053#1072#1080#1084#1077#1085#1086#1074#1072#1085#1080#1077
  end
  object Label3: TLabel
    Left = 496
    Top = 200
    Width = 79
    Height = 13
    Caption = #1054#1090#1074#1077#1090#1089#1090#1074#1077#1085#1085#1099#1077
  end
  object Label4: TLabel
    Left = 8
    Top = 392
    Width = 75
    Height = 13
    Caption = #1044#1086#1082#1091#1084#1077#1085#1090#1072#1094#1080#1103
  end
  object Label5: TLabel
    Left = 496
    Top = 392
    Width = 19
    Height = 13
    Caption = #1058#1080#1087
  end
  object DBGrid1: TDBGrid
    Left = 8
    Top = 24
    Width = 1009
    Height = 120
    DataSource = Form1.DataSource1
    TabOrder = 0
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
  end
  object DBNavigator1: TDBNavigator
    Left = 8
    Top = 144
    Width = 1000
    Height = 25
    DataSource = Form1.DataSource1
    TabOrder = 1
  end
  object DBGrid2: TDBGrid
    Left = 8
    Top = 216
    Width = 465
    Height = 120
    DataSource = DataSource1
    TabOrder = 2
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
  end
  object DBGrid3: TDBGrid
    Left = 496
    Top = 216
    Width = 361
    Height = 120
    DataSource = DataSource2
    TabOrder = 3
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
  end
  object DBNavigator2: TDBNavigator
    Left = 8
    Top = 336
    Width = 460
    Height = 25
    DataSource = DataSource1
    TabOrder = 4
  end
  object DBNavigator3: TDBNavigator
    Left = 496
    Top = 336
    Width = 360
    Height = 25
    DataSource = DataSource2
    TabOrder = 5
  end
  object DBGrid4: TDBGrid
    Left = 8
    Top = 408
    Width = 465
    Height = 120
    DataSource = DataSource3
    TabOrder = 6
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
  end
  object DBNavigator4: TDBNavigator
    Left = 8
    Top = 528
    Width = 460
    Height = 25
    DataSource = DataSource3
    TabOrder = 7
  end
  object DBGrid5: TDBGrid
    Left = 496
    Top = 408
    Width = 361
    Height = 120
    DataSource = DataSource4
    TabOrder = 8
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
  end
  object DBNavigator5: TDBNavigator
    Left = 496
    Top = 528
    Width = 360
    Height = 25
    DataSource = DataSource4
    TabOrder = 9
  end
  object Button1: TButton
    Left = 1024
    Top = 24
    Width = 105
    Height = 41
    Caption = #1057#1087#1080#1089#1072#1090#1100
    TabOrder = 10
    OnClick = Button1Click
  end
  object Button2: TButton
    Left = 1024
    Top = 72
    Width = 105
    Height = 25
    Caption = ' '#1057#1086#1073#1089#1090#1074#1077#1085#1085#1086#1089#1090#1100' '#1056#1060
    TabOrder = 11
    OnClick = Button2Click
  end
  object Button3: TButton
    Left = 1024
    Top = 104
    Width = 105
    Height = 25
    Caption = #1057#1086#1073#1089#1090#1074#1077#1085#1085#1086#1089#1090#1100' '#1056#1050
    TabOrder = 12
    OnClick = Button3Click
  end
  object Button4: TButton
    Left = 872
    Top = 344
    Width = 129
    Height = 25
    Caption = #1055#1086' '#1087#1086#1076#1088#1072#1079#1076#1077#1083#1077#1085#1080#1103#1084
    TabOrder = 13
    OnClick = Button4Click
  end
  object Button5: TButton
    Left = 872
    Top = 248
    Width = 129
    Height = 25
    Caption = #1055#1086' '#1089#1086#1089#1090#1086#1103#1085#1080#1102
    TabOrder = 14
    OnClick = Button5Click
  end
  object Button6: TButton
    Left = 872
    Top = 280
    Width = 129
    Height = 25
    Caption = #1055#1086' '#1086#1090#1074#1077#1090#1089#1090#1074#1077#1085#1085#1086#1084#1091
    TabOrder = 15
    OnClick = Button6Click
  end
  object Edit2: TEdit
    Left = 1008
    Top = 248
    Width = 121
    Height = 21
    TabOrder = 16
  end
  object Edit3: TEdit
    Left = 1008
    Top = 280
    Width = 121
    Height = 21
    TabOrder = 17
  end
  object Button7: TButton
    Left = 872
    Top = 312
    Width = 129
    Height = 25
    Caption = #1055#1086' '#1076#1072#1090#1077' '#1089#1087#1080#1089#1072#1085#1080#1103
    TabOrder = 18
    OnClick = Button7Click
  end
  object Edit4: TEdit
    Left = 1008
    Top = 312
    Width = 121
    Height = 21
    TabOrder = 19
  end
  object DBComboBox1: TDBComboBox
    Left = 1008
    Top = 344
    Width = 121
    Height = 21
    DataField = #1055#1086#1076#1088#1072#1079#1076#1077#1083#1077#1085#1080#1103
    DataSource = DataSource5
    ItemHeight = 13
    TabOrder = 20
  end
  object DBGrid6: TDBGrid
    Left = 904
    Top = 408
    Width = 225
    Height = 120
    DataSource = DataSource5
    TabOrder = 21
    TitleFont.Charset = DEFAULT_CHARSET
    TitleFont.Color = clWindowText
    TitleFont.Height = -11
    TitleFont.Name = 'MS Sans Serif'
    TitleFont.Style = []
  end
  object DBNavigator6: TDBNavigator
    Left = 904
    Top = 528
    Width = 220
    Height = 25
    DataSource = DataSource5
    TabOrder = 22
  end
  object ADOTable1: TADOTable
    Active = True
    Connection = Form1.ADOConnection1
    CursorType = ctStatic
    TableName = #1054#1090#1074#1077#1090#1089#1090#1074#1077#1085#1085#1099#1077
    Left = 672
    Top = 272
  end
  object ADOTable2: TADOTable
    Active = True
    Connection = Form1.ADOConnection1
    CursorType = ctStatic
    TableName = #1053#1072#1080#1084#1077#1085#1086#1074#1072#1085#1080#1077
    Left = 200
    Top = 296
  end
  object DataSource1: TDataSource
    DataSet = ADOTable2
    Left = 200
    Top = 272
  end
  object DataSource2: TDataSource
    DataSet = ADOTable1
    Left = 616
    Top = 272
  end
  object ADOQuery1: TADOQuery
    Active = True
    Connection = Form1.ADOConnection1
    CursorType = ctStatic
    Parameters = <>
    SQL.Strings = (
      'select * from '#1044#1086#1082#1091#1084#1077#1085#1090#1072#1094#1080#1103)
    Left = 88
    Top = 432
  end
  object DataSource3: TDataSource
    DataSet = ADOQuery1
    Left = 88
    Top = 464
  end
  object ADOQuery2: TADOQuery
    Active = True
    Connection = Form1.ADOConnection1
    CursorType = ctStatic
    Parameters = <>
    SQL.Strings = (
      'select * from '#1058#1080#1087)
    Left = 552
    Top = 448
  end
  object DataSource4: TDataSource
    DataSet = ADOQuery2
    Left = 544
    Top = 480
  end
  object ADOQuery3: TADOQuery
    Connection = Form1.ADOConnection1
    Parameters = <>
    Left = 1136
    Top = 24
  end
  object DataSource5: TDataSource
    DataSet = ADOTable3
    Left = 872
    Top = 408
  end
  object ADOTable3: TADOTable
    Active = True
    Connection = Form1.ADOConnection1
    CursorType = ctStatic
    TableName = #1055#1086#1076#1088#1072#1079#1076#1077#1083#1077#1085#1080#1103
    Left = 864
    Top = 440
  end
end
