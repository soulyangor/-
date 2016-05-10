//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit3.h"
#include "Unit1.h"
#include "Unit2.h"
#include "Unit4.h"
#include "Unit5.h"
#include "Unit6.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm3 *Form3;
//---------------------------------------------------------------------------
__fastcall TForm3::TForm3(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm3::FormCreate(TObject *Sender)
{

DBGrid1->DataSource->DataSet->FieldByName("����������� �����")->DisplayWidth=8;
DBGrid1->DataSource->DataSet->FieldByName("������������")->DisplayWidth=10;
DBGrid1->DataSource->DataSet->FieldByName("�������")->DisplayWidth=8;
DBGrid1->DataSource->DataSet->FieldByName("�������������")->DisplayWidth=10;
DBGrid1->DataSource->DataSet->FieldByName("��/��")->DisplayWidth=8;
DBGrid1->DataSource->DataSet->FieldByName("��������� �����")->DisplayWidth=8;
DBGrid1->DataSource->DataSet->FieldByName("��������")->DisplayWidth=10;
DBGrid1->DataSource->DataSet->FieldByName("���������")->DisplayWidth=8;

DBGrid2->DataSource->DataSet->FieldByName("���")->DisplayWidth=10;
DBGrid2->DataSource->DataSet->FieldByName("������������")->DisplayWidth=10;
DBGrid2->DataSource->DataSet->FieldByName("�������������")->DisplayWidth=10;
DBGrid2->DataSource->DataSet->FieldByName("���� �� ��")->DisplayWidth=10;
DBGrid2->DataSource->DataSet->FieldByName("���������")->DisplayWidth=10;

DBGrid3->DataSource->DataSet->FieldByName("�������")->DisplayWidth=10;
DBGrid3->DataSource->DataSet->FieldByName("���")->DisplayWidth=10;
DBGrid3->DataSource->DataSet->FieldByName("�������������")->DisplayWidth=10;
DBGrid3->DataSource->DataSet->FieldByName("���������")->DisplayWidth=10;

DBGrid4->DataSource->DataSet->FieldByName("���")->DisplayWidth=10;
DBGrid4->DataSource->DataSet->FieldByName("�������� ��������")->DisplayWidth=10;
DBGrid4->DataSource->DataSet->FieldByName("�������� ��������")->DisplayWidth=10;

DBGrid5->DataSource->DataSet->FieldByName("���")->DisplayWidth=10;
DBGrid5->DataSource->DataSet->FieldByName("��������")->DisplayWidth=10;
DBGrid5->DataSource->DataSet->FieldByName("�������")->DisplayWidth=10;
DBGrid5->DataSource->DataSet->FieldByName("��������")->DisplayWidth=10;
DBGrid5->DataSource->DataSet->FieldByName("���")->DisplayWidth=10;

}
//---------------------------------------------------------------------------


void __fastcall TForm3::Button1Click(TObject *Sender)
{
TDateTime CurrentDate = Date();
ADOQuery3->Active=false;
    ADOQuery3->SQL->Clear();
    AnsiString strSQL("SELECT �������.[����������� �����], �������.������������, �������.�������, �������������.���, �������.[��/��], �������.[��������� �����], ������������.���������, �������.[���� ��������], ������������.[�������� ��������] FROM ������������ INNER JOIN (������������� INNER JOIN (������������ INNER JOIN ������� ON ������������.��� = �������.��������) ON �������������.������� = �������.�������) ON ������������.������������ = �������.������������ WHERE (((�������.[���� ��������])<:ID));");
    ADOQuery3->SQL->Text=strSQL;
    ADOQuery3->Parameters->ParamByName("ID")->Value=CurrentDate;
    ADOQuery3->Open();
    ADOQuery3->Active=true;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[0]->DisplayWidth=8;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[1]->DisplayWidth=8;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[2]->DisplayWidth=8;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[3]->DisplayWidth=32;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[4]->DisplayWidth=10;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[5]->DisplayWidth=10;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[6]->DisplayWidth=10;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[7]->DisplayWidth=10;
    Form4->DBGrid1->DataSource->DataSet->Fields->Fields[8]->DisplayWidth=10;
    Form4->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm3::Button2Click(TObject *Sender)
{
    Form5->ADOQuery1->Active=false;
    Form5->ADOQuery1->SQL->Clear();
    AnsiString strSQL("SELECT �������.[����������� �����], �������.������������, �������.�������, �������������.���, �������.�������������, �������.[��������� �����], ������������.[�������� ��������], ������������.[�������� ��������], �������.���������, �������.[��/��] FROM ������������� INNER JOIN (������������ INNER JOIN ������� ON ������������.��� = �������.��������) ON �������������.������� = �������.������� WHERE (((�������.[��/��])=:ID));");
    Form5->ADOQuery1->SQL->Text=strSQL;
    Form5->ADOQuery1->Parameters->ParamByName("ID")->Value="RF";
    Form5->ADOQuery1->Open();
    Form5->ADOQuery1->Active=true;

    Form5->Label1->Caption="������������� ��";

    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[0]->DisplayWidth=8;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[1]->DisplayWidth=8;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[2]->DisplayWidth=8;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[3]->DisplayWidth=32;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[4]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[5]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[6]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[7]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[8]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[9]->DisplayWidth=10;
    Form5->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm3::Button3Click(TObject *Sender)
{
Form5->ADOQuery1->Active=false;
    Form5->ADOQuery1->SQL->Clear();
    AnsiString strSQL("SELECT �������.[����������� �����], �������.������������, �������.�������, �������������.���, �������.�������������, �������.[��������� �����], ������������.[�������� ��������], ������������.[�������� ��������], �������.���������, �������.[��/��] FROM ������������� INNER JOIN (������������ INNER JOIN ������� ON ������������.��� = �������.��������) ON �������������.������� = �������.������� WHERE (((�������.[��/��])=:ID));");
    Form5->ADOQuery1->SQL->Text=strSQL;
    Form5->ADOQuery1->Parameters->ParamByName("ID")->Value="RK";
    Form5->ADOQuery1->Open();
    Form5->ADOQuery1->Active=true;

    Form5->Label1->Caption="������������� ��";

    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[0]->DisplayWidth=8;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[1]->DisplayWidth=8;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[2]->DisplayWidth=8;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[3]->DisplayWidth=32;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[4]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[5]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[6]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[7]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[8]->DisplayWidth=10;
    Form5->DBGrid1->DataSource->DataSet->Fields->Fields[9]->DisplayWidth=10;
    Form5->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm3::Button4Click(TObject *Sender)
{
    Form6->ADOQuery1->Active=false;
    Form6->ADOQuery1->SQL->Clear();
    AnsiString strSQL("SELECT �������.[����������� �����], �������.������������, �������.�������, �������������.���, �������.�������������, �������.[��������� �����], ������������.[�������� ��������], ������������.[�������� ��������], �������.���������, �������.[��/��] FROM ������������� INNER JOIN (������������ INNER JOIN ������� ON ������������.��� = �������.��������) ON �������������.������� = �������.������� WHERE (((�������.�������������)=:ID));");
    Form6->ADOQuery1->SQL->Text=strSQL;
    Form6->ADOQuery1->Parameters->ParamByName("ID")->Value=DBComboBox1->Text;
    Form6->ADOQuery1->Open();
    Form6->ADOQuery1->Active=true;

    Form6->Label1->Caption="�� ��������������";

    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[0]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[1]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[2]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[3]->DisplayWidth=32;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[4]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[5]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[6]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[7]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[8]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[9]->DisplayWidth=10;
    Form6->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm3::Button5Click(TObject *Sender)
{
    Form6->ADOQuery1->Active=false;
    Form6->ADOQuery1->SQL->Clear();
    AnsiString strSQL("SELECT �������.[����������� �����], �������.������������, �������.�������, �������������.���, �������.�������������, �������.[��������� �����], ������������.[�������� ��������], ������������.[�������� ��������], �������.���������, �������.[��/��] FROM ������������� INNER JOIN (������������ INNER JOIN ������� ON ������������.��� = �������.��������) ON �������������.������� = �������.������� WHERE (((�������.���������)=:ID));");
    Form6->ADOQuery1->SQL->Text=strSQL;
    Form6->ADOQuery1->Parameters->ParamByName("ID")->Value=Edit2->Text;
    Form6->ADOQuery1->Open();
    Form6->ADOQuery1->Active=true;

    Form6->Label1->Caption="�� ���������";

    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[0]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[1]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[2]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[3]->DisplayWidth=32;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[4]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[5]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[6]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[7]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[8]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[9]->DisplayWidth=10;
    Form6->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm3::Button6Click(TObject *Sender)
{
    Form6->ADOQuery1->Active=false;
    Form6->ADOQuery1->SQL->Clear();
    AnsiString strSQL("SELECT �������.[����������� �����], �������.������������, �������.�������, �������������.���, �������.�������������, �������.[��������� �����], ������������.[�������� ��������], ������������.[�������� ��������], �������.���������, �������.[��/��] FROM ������������� INNER JOIN (������������ INNER JOIN ������� ON ������������.��� = �������.��������) ON �������������.������� = �������.������� WHERE (((�������������.���)=:ID));");
    Form6->ADOQuery1->SQL->Text=strSQL;
    Form6->ADOQuery1->Parameters->ParamByName("ID")->Value=Edit3->Text;
    Form6->ADOQuery1->Open();
    Form6->ADOQuery1->Active=true;

    Form6->Label1->Caption="�� ��������������";

    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[0]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[1]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[2]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[3]->DisplayWidth=32;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[4]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[5]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[6]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[7]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[8]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[9]->DisplayWidth=10;
    Form6->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm3::Button7Click(TObject *Sender)
{
    Form6->ADOQuery1->Active=false;
    Form6->ADOQuery1->SQL->Clear();
    AnsiString strSQL("SELECT �������.[����������� �����], �������.������������, �������.�������, �������������.���, �������.[��/��], �������.[��������� �����], ������������.���������, �������.[���� ��������], ������������.[�������� ��������] FROM ������������ INNER JOIN (������������� INNER JOIN (������������ INNER JOIN ������� ON ������������.��� = �������.��������) ON �������������.������� = �������.�������) ON ������������.������������ = �������.������������ WHERE (((�������.[���� ��������])<:ID));");
    Form6->ADOQuery1->SQL->Text=strSQL;
    Form6->ADOQuery1->Parameters->ParamByName("ID")->Value=Edit4->Text;
    Form6->ADOQuery1->Open();
    Form6->ADOQuery1->Active=true;

    Form6->Label1->Caption="� "+Edit4->Text+" ����� �������:";

    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[0]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[1]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[2]->DisplayWidth=8;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[3]->DisplayWidth=32;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[4]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[5]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[6]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[7]->DisplayWidth=10;
    Form6->DBGrid1->DataSource->DataSet->Fields->Fields[8]->DisplayWidth=10;
    Form6->Show();
}
//---------------------------------------------------------------------------




void __fastcall TForm3::FormClose(TObject *Sender, TCloseAction &Action)
{
  Form1->FormStyle=fsStayOnTop;        
}
//---------------------------------------------------------------------------

