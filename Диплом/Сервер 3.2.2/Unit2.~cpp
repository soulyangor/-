//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit2.h"
#include "Unit1.h"
#include "Unit3.h"
#include "Unit4.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm2 *Form2;
//---------------------------------------------------------------------------
__fastcall TForm2::TForm2(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button1Click(TObject *Sender)
{
ADOQuery1->Close();
ADOQuery1->SQL->Text = "SELECT resolution FROM Users WHERE log = :p_log AND pass = :p_pass";
ADOQuery1->Parameters->ParamByName("p_log")->Value = "log";
ADOQuery1->Parameters->ParamByName("p_pass")->Value = "pass";
ADOQuery1->Open();

Form1->Show();
}
//---------------------------------------------------------------------------
