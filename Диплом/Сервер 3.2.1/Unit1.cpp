//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit1.h"
#include "Unit2.h"
#include "Unit3.h"
#include "Unit4.h"
#include "Unit7.h"

#include      <ComObj.hpp>
#include      <utilcls.h>
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "Word_2K_SRVR"
#pragma resource "*.dfm"
TForm1 *Form1;
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------

class TCustomException{}; //

TCustomException NewEx;
bool start=false;
void __fastcall TForm1::Button1Click(TObject *Sender)
{
start=true;
ServerSocket1->Active = true ;
Memo1->Lines->Add("������ �������");
}
//---------------------------------------------------------------------------

bool onEdit;
void __fastcall TForm1::ServerSocket1Accept(TObject *Sender,
      TCustomWinSocket *Socket)
{
     Timer1->Enabled=true;
     onEdit=true;
     String message = Socket->RemoteAddress;
     Memo1->Lines->Add("IP-������ ��������� ������: "+message);
     int *a;
     int Bufer = Socket->ReceiveLength();
     char * Bufs = new char[Bufer+1];
     Socket->ReceiveBuf(Bufs, Bufer);

     int si=0;
     int k=0;

     String str[20];
     while(si<Bufer)
     {
          for (int i=si;i<Bufer;i++)
                        if((Bufs[i]=='/')||(i==Bufer-1))
                                {
                                  si=i+1;
                                  break;
                                } else str[k]+=Bufs[i];
        k++;
     }


     Bufs[Bufer+1]=' ';
     Bufs[Bufer]=' ';
     message=Bufs;

   bool flag=ADOTable1->Locate("����������� �����",str[0],TLocateOptions()<<loCaseInsensitive);

    if((!flag)&&(k>8))
    {
     ADOTable1->Append();
     ADOTable1->FieldValues["����������� �����"]=str[0];
     ADOTable1->FieldValues["������������"]=str[1];
     ADOTable1->FieldValues["�������"]=str[2];
     ADOTable1->FieldValues["�������������"]=str[3];
     ADOTable1->FieldValues["��/��"]=str[4];
     TDateTime CurrentDate = Date();
     ADOTable1->FieldValues["���� ��������������"]=CurrentDate;
     ADOTable1->FieldValues["���� ��������"]=str[5];
     ADOTable1->FieldValues["���� ��������"]=str[6];
     ADOTable1->FieldValues["��������� �����"]=str[7];
     ADOTable1->FieldValues["��������"]=str[8];
     ADOTable1->FieldValues["���������"]=str[9];
     ADOTable1->Post();
     Memo1->Lines->Add("������ ��������");
     ClientSocket1->Active=true;
     ClientSocket1->Socket->SendText("Success!");
     ClientSocket1->Active=false;
    }else
    {
      if(flag)
      {
      Memo1->Lines->Add("��� ���� ������");
      ClientSocket1->Active=true;
      ClientSocket1->Socket->SendText("Already exist!");
      ClientSocket1->Active=false;
      }
      if(k<8)
      {
      Memo1->Lines->Add("������ ��������, ��������� �������");
      ClientSocket1->Active=true;
      ClientSocket1->Socket->SendText("Fail! Try again!");
      ClientSocket1->Active=false;
      }
    }
    ADOQuery1->Active=false;
    ADOQuery1->SQL->Clear();
    AnsiString strSQL("SELECT Count(�������.������������) AS [����������], ������������.������������ FROM ������������ INNER JOIN ������� ON ������������.������������ = �������.������������ GROUP BY ������������.������������ HAVING (((������������.������������)=:ID));");
    ADOQuery1->SQL->Text=strSQL;
    ADOQuery1->Parameters->ParamByName("ID")->Value=str[1];
    ADOQuery1->Open();
    ADOQuery1->Active=true;
    ADOTable2->First();
    while(!ADOTable2->Eof) {
        if(ADOTable2->FieldByName("������������")->AsString==str[1])
        {
        ADOTable2->Edit();
        ADOTable2->FieldByName("����������")->AsInteger = ADOQuery1->Fields->Fields[0]->AsInteger ;
        ADOTable2->Post();
        break;
        }
        ADOTable2->Next();
    }
    onEdit=false;
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Button2Click(TObject *Sender)
{
   start=false;
   ServerSocket1->Active = false ;
   Memo1->Lines->Add("������ ����������");
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Button3Click(TObject *Sender)
{
   Timer1->Enabled=true;
   Memo1->Lines->Add("������ �������������");
}
//---------------------------------------------------------------------------


//---------------------------------------------------------------------------





void __fastcall TForm1::FormCreate(TObject *Sender)
{
DBGrid1->DataSource->DataSet->FieldByName("�������")->DisplayWidth=8;
DBGrid1->DataSource->DataSet->FieldByName("�������������")->DisplayWidth=10;
DBGrid1->DataSource->DataSet->FieldByName("���������")->DisplayWidth=8;

}
//---------------------------------------------------------------------------

void __fastcall TForm1::FormResize(TObject *Sender)
{
   /*DBGrid1->ClientWidth   */
}
//---------------------------------------------------------------------------




void __fastcall TForm1::Button4Click(TObject *Sender)
{
PasswordDlg->Enter=0;
PasswordDlg->ShowModal();
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button5Click(TObject *Sender)
{
  Form1->FormStyle=fsNormal;
  Form3->Show();
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Button6Click(TObject *Sender)
{
  ServerSocket1->Port=StrToInt(Edit1->Text);
}
//---------------------------------------------------------------------------

void __fastcall TForm1::FormClose(TObject *Sender, TCloseAction &Action)
{
PasswordDlg->Close();
}
//---------------------------------------------------------------------------
int k=0;
void __fastcall TForm1::Timer1Timer(TObject *Sender)
{
ServerSocket1->Active=false;
k++;
if(k==2){
ServerSocket1->Active=true;
Timer1->Enabled=false;
k=0;
}

}
//---------------------------------------------------------------------------

int iz=0;
void __fastcall TForm1::DBGrid1DrawColumnCell(TObject *Sender,
      const TRect &Rect, int DataCol, TColumn *Column,
      TGridDrawState State)
{
  iz=0;
  if((!onEdit)&&(ADOTable1->RecordCount!=0))
  {
  String k=ADOTable1->FieldByName("�������")->Value;
  if(k!=Edit2->Text)
  {
     iz++;
     TField *F = Column->Field;
     TCanvas *Cvs = DBGrid1->Canvas;
     Cvs->Brush->Color = clRed;
     Cvs->FillRect(Rect);
     Cvs->Font->Color = clWhite;
     Cvs->TextOut(Rect.Left+2, Rect.Top+2, F->Text);
     Cvs->TextOut(Rect.Left+2, Rect.Top+2, F->Text);
   }
  else DBGrid1->DefaultDrawColumnCell(Rect,DataCol,Column,State);
  }
}
//---------------------------------------------------------------------------


void __fastcall TForm1::DBNavigator1Click(TObject *Sender,
      TNavigateBtn Button)
{
onEdit=false;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::DBGrid1CellClick(TColumn *Column)
{
onEdit=true;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::DBGrid1ColEnter(TObject *Sender)
{
 onEdit=true;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::DBGrid1EditButtonClick(TObject *Sender)
{
  onEdit=true;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::DBGrid1Enter(TObject *Sender)
{
  onEdit=true;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button7Click(TObject *Sender)
{
AnsiString filename = Edit3->Text;
if (FileExists(filename)) {WinExec(filename.c_str(), SW_SHOW);}
else {ShowMessage("��� �����");}
}
//---------------------------------------------------------------------------



void __fastcall TForm1::Button8Click(TObject *Sender)
{
OpenDialog1->Execute();
Edit3->Text=OpenDialog1->FileName;
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Button9Click(TObject *Sender)
{
Button10->Visible=false;
Button9->Visible=false;
Variant v,v1;
 if(!fStart)
 {
  try
  {
   //������� ������ Word.Application
   vVarApp=CreateOleObject("Word.Application");
   fStart=true;
  }
  catch(...)
  {
   MessageBox(0, "������ ��� �������� ������� Word",
                 "������", MB_OK);
  return;
  }
 }
 //������ ������ �������
 vVarApp.OlePropertySet("Visible",true);
 //�������� �������� Documents �������
 vVarDocs=vVarApp.OlePropertyGet("Documents");
 //������� ��� ���������
 vVarDocs.OleProcedure("Add");

 vVarDoc=vVarDocs.OleFunction("Item",1);
 //�������� ����� ���� �� ������ ��������
 vVarDoc.OleProcedure("Activate");
 //vVarDoc.OleProcedure("Select");
 vVarParagraphs=vVarDoc.OlePropertyGet("Paragraphs");

 vVarDoc.OlePropertyGet("PageSetup").OlePropertySet("Orientation",1);

 //������ ����� ��� ������
 str="\t\t\"���������\"    \t\t";
 //��������� ��������
 vVarParagraphs.OleProcedure("Add");
 //�������� � ������ ����������
 vVarParagraph=vVarParagraphs.OleFunction("Item",1);
 //������� � ���� �����
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //����������� �� ������� ����
 vVarParagraph.OlePropertySet("Alignment",2);
 //������ ��������
 str="\t\t��������� ���� ������� ���� \"�����\"\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",2);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);
 //������ ��������
 str="\t\t\"����������� ����� \"�����\"\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",3);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);

 //��������� ��������
  str="\t\t_____________________\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",4);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);

 str="\t\t\"_____\"______________20__����\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",5);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);

 //������ ��������
 str="������������������ ��������� � �������� "+Edit2->Text;
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",6);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",1);

 str="�������� ";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",7);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter

 vVarParagraph.OlePropertySet("Alignment",0);
  str=Edit4->Text+"    "+Edit5->Text;
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",8);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 TDateTime CurrentDate = Date();
 str="�� "+CurrentDate;
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",9);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",1);

 vVarParagraphs.OleProcedure("Add");
 vVarParagraphs.OleProcedure("Add");

 str="����� ��������_____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",12);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 str="                                   _____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",13);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 str="                                   _____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",14);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 str="                                   _____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",15);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);


 //������� ����������� ���������
 vVarApp.OlePropertyGet("ActiveWindow").
         OlePropertyGet("ActivePane").
         OlePropertyGet("View").
         OlePropertyGet("Zoom").
         OlePropertySet("Percentage",100);
 //�������� ����������
  vVarApp.OlePropertyGet("Options").
          OlePropertySet("CheckGrammarAsYouType",false);
  vVarApp.OlePropertyGet("Options").
          OlePropertySet("CheckGrammarWithSpelling",false);
 //�����  ������������� ��� ������ ���������
 vVarParagraph=vVarParagraphs.OleFunction("Item",6);
 v=vVarParagraph.OlePropertyGet("Range").
               OlePropertyGet("Font");

 //������ ������
 v.OlePropertySet("Size",18);
 //��� ������
 v.OlePropertySet("Name","Times New Roman");
 //����� ������
 v.OlePropertySet("Bold",true);

 //��� ����� � ������� ��������
 v.OlePropertySet("AllCaps",false);
 //������������� ����� ����������
  //� ������� �������
  v.OlePropertySet("Superscript",false);
 //� ������ �������
  v.OlePropertySet("Subscript",false);
 //���������� ����� �������
  v.OlePropertySet("Spacing",0);
 //�������
  v.OlePropertySet("Scaling",100);
 //��������� ����� ��� ���� � ��������
  v.OlePropertySet("Position",0);
 //������� - ������� � �������
 v.OlePropertySet("Kerning",1);
 //��������
 v.OlePropertySet("Animation",0);
 //���������� ��� ������ - �������� Selection
 v=vVarApp.OlePropertyGet("Selection");
 //������� ������ �� 5 ���������� ����
 v.OleProcedure("MoveDown",4,5);
 //������� �� ������ ������
 v.OleProcedure("HomeKey",5);
 //���������� �� 13 �������� ������
 v.OleProcedure("MoveRight",1,13);

 ///////////////////////C������� �������//////////
 //��������� ��� ���������

 //�������� ��� � ���������
 vVarParagraph=vVarParagraphs.OleFunction("Item",10);
 vVarParagraph.OlePropertySet("Alignment",1);
 vVarRange = vVarParagraph.OlePropertyGet("Range");
 //��������� �������
 vVarDoc.OlePropertyGet("Tables").
         OleProcedure("Add", vVarRange, ADOTable1->RecordCount+1, 11,1,1);
 //�������� ����� � ������ ��������
 vVarTable=vVarDoc.OlePropertyGet("Tables").
                   OleFunction("Item",1);
 //����������� ������� �� ������
 vVarTable.OlePropertyGet("Rows").
           OlePropertySet("Alignment",1);
 //��������� ������ �������
 vVarTable.OleFunction("AutoFitBehavior",2);
 //����� � �������
  vVarApp.OlePropertyGet("ActiveWindow").
          OlePropertyGet("View").
          OlePropertySet("TableGridlines",true);
 //����� �������
 vVarTable.OleFunction("AutoFormat",5);
 //��������� ���������� � ������

  for(int i=1;i <= 11;i++)
  {
        String txt=ADOTable1->Fields->Fields[i-1]->FieldName;
        vVarCell=vVarTable.OleFunction("Cell",1,i);
        vVarCell.OleFunction("Select");
        v = vVarApp.OlePropertyGet("Selection").
        OlePropertyGet("Font");
        v.OlePropertySet("Size",16);
        v.OlePropertySet("Underline",0);
        vVarCell.OlePropertyGet("Range").
        OlePropertySet("Text",txt.c_str());
  }

  int k=2;
  ADOTable1->First();
  while(!ADOTable1->Eof) {
        for(int i=1;i <= 11;i++)
        {
        String txt=ADOTable1->Fields->Fields[i-1]->AsString;

        vVarCell=vVarTable.OleFunction("Cell",k,i);
        vVarCell.OleFunction("Select");
        v = vVarApp.OlePropertyGet("Selection").
        OlePropertyGet("Font");
        v.OlePropertySet("Size",16);
        v.OlePropertySet("Underline",0);
        vVarCell.OlePropertyGet("Range").
        OlePropertySet("Text",txt.c_str());
        }
        k++;
        ADOTable1->Next();
    }

 AnsiString vAsCurDir=Edit6->Text.c_str();
 AnsiString vAsCurDir1=vAsCurDir+"\\�������.doc";
 vVarDoc=vVarDocs.OleFunction("Item",1);
 vVarDoc.OleProcedure("SaveAs",vAsCurDir1.c_str());
 vVarApp.OleProcedure("Quit");
 fStart=false;
Button10->Visible=true;
Button9->Visible=true;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button10Click(TObject *Sender)
{
Button10->Visible=false;
Button9->Visible=false;
Variant v,v1;
 if(!fStart)
 {
  try
  {
   //������� ������ Word.Application
   vVarApp=CreateOleObject("Word.Application");
   fStart=true;
  }
  catch(...)
  {
   MessageBox(0, "������ ��� �������� ������� Word",
                 "������", MB_OK);
  return;
  }
 }
 //������ ������ �������
 vVarApp.OlePropertySet("Visible",true);
 //�������� �������� Documents �������
 vVarDocs=vVarApp.OlePropertyGet("Documents");
 //������� ��� ���������
 vVarDocs.OleProcedure("Add");

 vVarDoc=vVarDocs.OleFunction("Item",1);
 //�������� ����� ���� �� ������ ��������
 vVarDoc.OleProcedure("Activate");
 //vVarDoc.OleProcedure("Select");
 vVarParagraphs=vVarDoc.OlePropertyGet("Paragraphs");

 vVarDoc.OlePropertyGet("PageSetup").OlePropertySet("Orientation",1);

 //������ ����� ��� ������
 str="\t\t\"���������\"     \t\t";
 //��������� ��������
 vVarParagraphs.OleProcedure("Add");
 //�������� � ������ ����������
 vVarParagraph=vVarParagraphs.OleFunction("Item",1);
 //������� � ���� �����
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //����������� �� ������� ����
 vVarParagraph.OlePropertySet("Alignment",2);
 //������ ��������
 str="\t\t��������� ���� ������� ���� \"�����\"\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",2);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);
 //������ ��������
 str="\t\t\"����������� ����� \"�����\"\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",3);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);

 //��������� ��������
  str="\t\t_____________________\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",4);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);

 str="\t\t\"_____\"______________20__����\t\t";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",5);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 vVarParagraph.OlePropertySet("Alignment",2);

 //������ ��������
 str="����������� ���";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",6);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",1);

 str="�������� ";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",7);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter

 vVarParagraph.OlePropertySet("Alignment",0);
  str=Edit4->Text+"    "+Edit5->Text;
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",8);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 TDateTime CurrentDate = Date();

 str="�������� �������� �� �� "+Edit2->Text+" ��������";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",9);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",1);

 str="�� "+CurrentDate;
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",10);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",1);
 vVarParagraphs.OleProcedure("Add");
 vVarParagraphs.OleProcedure("Add");


 str="������������� �������� ���������� �����������.";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",13);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);

 str="����� ��������_____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",14);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 str="                                   _____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",15);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 str="                                   _____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",16);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);
 str="                                   _____________________________________";
 vVarParagraphs.OleProcedure("Add");
 vVarParagraph=vVarParagraphs.OleFunction("Item",17);
 vVarParagraph.OlePropertyGet("Range").
               OlePropertySet("Text",str.c_str());
 //wdAlignParagraphCenter
 vVarParagraph.OlePropertySet("Alignment",0);


 //������� ����������� ���������
 vVarApp.OlePropertyGet("ActiveWindow").
         OlePropertyGet("ActivePane").
         OlePropertyGet("View").
         OlePropertyGet("Zoom").
         OlePropertySet("Percentage",100);
 //�������� ����������
  vVarApp.OlePropertyGet("Options").
          OlePropertySet("CheckGrammarAsYouType",false);
  vVarApp.OlePropertyGet("Options").
          OlePropertySet("CheckGrammarWithSpelling",false);
 //�����  ������������� ��� ������ ���������
 vVarParagraph=vVarParagraphs.OleFunction("Item",6);
 v=vVarParagraph.OlePropertyGet("Range").
               OlePropertyGet("Font");

 //������ ������
 v.OlePropertySet("Size",18);
 //��� ������
 v.OlePropertySet("Name","Times New Roman");
 //����� ������
 v.OlePropertySet("Bold",true);

 //��� ����� � ������� ��������
 v.OlePropertySet("AllCaps",false);
 //������������� ����� ����������
  //� ������� �������
  v.OlePropertySet("Superscript",false);
 //� ������ �������
  v.OlePropertySet("Subscript",false);
 //���������� ����� �������
  v.OlePropertySet("Spacing",0);
 //�������
  v.OlePropertySet("Scaling",100);
 //��������� ����� ��� ���� � ��������
  v.OlePropertySet("Position",0);
 //������� - ������� � �������
 v.OlePropertySet("Kerning",1);
 //��������
 v.OlePropertySet("Animation",0);
 //���������� ��� ������ - �������� Selection
 v=vVarApp.OlePropertyGet("Selection");
 //������� ������ �� 5 ���������� ����
 v.OleProcedure("MoveDown",4,5);
 //������� �� ������ ������
 v.OleProcedure("HomeKey",5);
 //���������� �� 13 �������� ������
 v.OleProcedure("MoveRight",1,13);

 ///////////////////////C������� �������//////////
 //��������� ��� ���������
 vVarParagraphs.OleProcedure("Add");
 vVarParagraphs.OleProcedure("Add");
 //�������� ��� � ���������
 vVarParagraph=vVarParagraphs.OleFunction("Item",11);
 vVarParagraph.OlePropertySet("Alignment",1);
 vVarRange = vVarParagraph.OlePropertyGet("Range");
 //��������� �������
 vVarDoc.OlePropertyGet("Tables").
         OleProcedure("Add", vVarRange, ADOTable1->RecordCount+1, 11,1,1);
 //�������� ����� � ������ ��������
 vVarTable=vVarDoc.OlePropertyGet("Tables").
                   OleFunction("Item",1);
 //����������� ������� �� ������
 vVarTable.OlePropertyGet("Rows").
           OlePropertySet("Alignment",1);
 //��������� ������ �������
 vVarTable.OleFunction("AutoFitBehavior",2);
 //����� � �������
  vVarApp.OlePropertyGet("ActiveWindow").
          OlePropertyGet("View").
          OlePropertySet("TableGridlines",true);
 //����� �������
 vVarTable.OleFunction("AutoFormat",25);
 //��������� ���������� � ������


  for(int i=1;i <= 11;i++)
  {
        String txt=ADOTable1->Fields->Fields[i-1]->FieldName;
        vVarCell=vVarTable.OleFunction("Cell",1,i);
        vVarCell.OleFunction("Select");
        v = vVarApp.OlePropertyGet("Selection").
        OlePropertyGet("Font");
        v.OlePropertySet("Size",16);
        v.OlePropertySet("Underline",0);
        vVarCell.OlePropertyGet("Range").
        OlePropertySet("Text",txt.c_str());
  }

  int k=2;
  ADOTable1->First();
  while(!ADOTable1->Eof) {
        if(ADOTable1->FieldByName("�������")->AsString==Edit2->Text)
        {
        ADOTable1->Next();
        continue;
        }
        for(int i=1;i <= 11;i++)
        {
        String txt=ADOTable1->Fields->Fields[i-1]->AsString;

        vVarCell=vVarTable.OleFunction("Cell",k,i);
        vVarCell.OleFunction("Select");
        v = vVarApp.OlePropertyGet("Selection").
        OlePropertyGet("Font");
        v.OlePropertySet("Size",16);
        v.OlePropertySet("Underline",0);
        vVarCell.OlePropertyGet("Range").
        OlePropertySet("Text",txt.c_str());
        }
        k++;
        ADOTable1->Next();
    }

 AnsiString vAsCurDir=Edit6->Text.c_str();
 AnsiString vAsCurDir1=vAsCurDir+"\\�����������.doc";
 vVarDoc=vVarDocs.OleFunction("Item",1);
 vVarDoc.OleProcedure("SaveAs",vAsCurDir1.c_str());
 vVarApp.OleProcedure("Quit");
 fStart=false;
Button10->Visible=true;
Button9->Visible=true;
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Button11Click(TObject *Sender)
{
ClientSocket1->Address=Edit8->Text;
ClientSocket1->Host=Edit7->Text;
}
//---------------------------------------------------------------------------


//---------------------------------------------------------------------------





