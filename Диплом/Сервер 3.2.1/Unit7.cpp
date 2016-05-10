//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit7.h"
#include "Unit1.h"
#include "Unit2.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TPasswordDlg *PasswordDlg;
TStringList* DataList=new TStringList;   
//---------------------------------------------------------------------------
__fastcall TPasswordDlg::TPasswordDlg(TComponent* Owner)
        : TForm(Owner)
{
   Enter=1;                                                                       
   p=17;
}
//---------------------------------------------------------------------------
void __fastcall TPasswordDlg::FormCreate(TObject *Sender)
{
//Form1->Show();
}
//---------------------------------------------------------------------------

void __fastcall TPasswordDlg::Button1Click(TObject *Sender)
{
if( (Edit1->Text!="") && (Edit2->Text!=""))
        {
        DataList->LoadFromFile("data.dat");
        if(!Enter){
        if(DataList->IndexOfName(Edit1->Text)==-1){
        DataList->Add(Edit1->Text+"="+Crypt(Edit2->Text));
        DataList->SaveToFile("data.dat");
        MessageBox(0,"Пользователь добавлен","Сообщение",0);
        PasswordDlg->Close();
        Hide();
        }
      else
      {
        DataList->Delete(DataList->IndexOfName(Edit1->Text));
        DataList->Add(Edit1->Text+"="+Crypt(Edit2->Text));
        DataList->SaveToFile("data.dat");
        MessageBox(0,"Пароль отредактирован","Сообщение",0);
        PasswordDlg->Close();
        Hide();
      }}

        if(Enter){
 {
 if(DataList->Count==0)
 {
   isok=1;
   MessageBox(0,"Вход выполнен","Проверка пройдена",0);
   Form1->Show();
   Hide();
 }else
 if(DataList->IndexOfName(Edit1->Text)!=-1){
 if(Edit2->Text==UnCrypt(DataList->Values[Edit1->Text]) )
   {
   isok=1;
   MessageBox(0,"Вход выполнен","Проверка пройдена",0);
   Form1->Show();
   Hide();
   }
 else MessageBox(0,"Не верный пароль.","Ошибка аторизации",0);
 }
 else MessageBox(0,"Не верный логин","Ошибка авторизации",0);
}
}
}
}

//---------------------------------------------------------------------------

void __fastcall TPasswordDlg::Button2Click(TObject *Sender)
{
Close();
}
//---------------------------------------------------------------------------

