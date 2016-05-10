//---------------------------------------------------------------------------

#ifndef Unit7H
#define Unit7H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
//---------------------------------------------------------------------------
class TPasswordDlg : public TForm
{
__published:	// IDE-managed Components
        TEdit *Edit1;
        TEdit *Edit2;
        TButton *Button1;
        TButton *Button2;
        TLabel *Label1;
        TLabel *Label2;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall Button2Click(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TPasswordDlg(TComponent* Owner);
        bool Enter;
        bool isok;
        String Crypt(String Data);
        String UnCrypt(String Crypt);
        int p;
};
String TPasswordDlg::Crypt(String Data){
   String rString;
        for(int i=1;i<Data.Length()+1;i++)
 {
  rString=rString+String(IntToHex(int(Data[i]+p),2));
   }
  return rString;
 }

   String TPasswordDlg::UnCrypt(String Crypt){

   String rString;

        for(int i=1;i<Crypt.Length()+1;i+=2)
 {
  rString=rString+String( char( StrToInt("0x"+Crypt.SubString(i,2))-p )   );
   }
  return rString;                                         
 }

//---------------------------------------------------------------------------
extern PACKAGE TPasswordDlg *PasswordDlg;
//---------------------------------------------------------------------------
#endif
