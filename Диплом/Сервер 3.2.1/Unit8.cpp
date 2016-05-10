//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit8.h"
#include "Unit7.h"
#include "Unit9.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm8 *Form8;
//---------------------------------------------------------------------------
__fastcall TForm8::TForm8(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------

void __fastcall TForm8::Button1Click(TObject *Sender)
{
PasswordDlg->Hide();
PasswordDlg->Show();
}
//---------------------------------------------------------------------------
void __fastcall TForm8::Button2Click(TObject *Sender)
{
 SendMessage(Handle,WM_CLOSE,NULL,NULL);
}
//---------------------------------------------------------------------------


void __fastcall TForm8::Button3Click(TObject *Sender)
{
Form9->Show();
}
//---------------------------------------------------------------------------

