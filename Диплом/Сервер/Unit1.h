//---------------------------------------------------------------------------

#ifndef Unit1H
#define Unit1H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ScktComp.hpp>
#include <IdBaseComponent.hpp>
#include <IdComponent.hpp>
#include <IdTCPServer.hpp>
#include <ADODB.hpp>
#include <DB.hpp>
#include <DBCtrls.hpp>
#include <DBGrids.hpp>
#include <ExtCtrls.hpp>
#include <Grids.hpp>
#include <Dialogs.hpp>
#include "Word_2K_SRVR.h"
#include <OleServer.hpp>
//---------------------------------------------------------------------------
class TForm1 : public TForm
{
__published:	// IDE-managed Components
        TServerSocket *ServerSocket1;
        TButton *Button1;
        TMemo *Memo1;
        TButton *Button2;
        TADOConnection *ADOConnection1;
        TButton *Button3;
        TDataSource *DataSource1;
        TDBGrid *DBGrid1;
        TDBNavigator *DBNavigator1;
        TADOTable *ADOTable1;
        TLabel *Label1;
        TLabel *Label2;
        TButton *Button4;
        TButton *Button5;
        TADOTable *ADOTable2;
        TADOQuery *ADOQuery1;
        TButton *Button6;
        TEdit *Edit1;
        TEdit *Edit2;
        TLabel *Label3;
        TButton *Button7;
        TEdit *Edit3;
        TLabel *Label4;
        TButton *Button8;
        TOpenDialog *OpenDialog1;
        TButton *Button9;
        TButton *Button10;
        TEdit *Edit4;
        TLabel *Label5;
        TLabel *Label6;
        TEdit *Edit5;
        TLabel *Label7;
        TEdit *Edit6;
        TLabel *Label8;
        TButton *Button11;
        void __fastcall Button1Click(TObject *Sender);
        
        void __fastcall ServerSocket1Accept(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall Button2Click(TObject *Sender);
        void __fastcall Button3Click(TObject *Sender);
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall FormResize(TObject *Sender);
        void __fastcall Button4Click(TObject *Sender);
        void __fastcall Button5Click(TObject *Sender);
        void __fastcall Button6Click(TObject *Sender);
        void __fastcall FormClose(TObject *Sender, TCloseAction &Action);
        void __fastcall Timer1Timer(TObject *Sender);
        void __fastcall DBGrid1DrawColumnCell(TObject *Sender,
          const TRect &Rect, int DataCol, TColumn *Column,
          TGridDrawState State);
        void __fastcall DBNavigator1Click(TObject *Sender,
          TNavigateBtn Button);
        void __fastcall DBGrid1CellClick(TColumn *Column);
        void __fastcall DBGrid1ColEnter(TObject *Sender);
        void __fastcall DBGrid1EditButtonClick(TObject *Sender);
        void __fastcall DBGrid1Enter(TObject *Sender);
        void __fastcall Button7Click(TObject *Sender);
        void __fastcall Button8Click(TObject *Sender);
        void __fastcall Button9Click(TObject *Sender);
        void __fastcall Button10Click(TObject *Sender);
        void __fastcall Button11Click(TObject *Sender);



private:	Variant         vVarApp,vVarDocs,vVarDoc,vVarParagraphs,
                  vVarParagraph,vVarRange,vVarTables,vVarTable,
                  vVarCell;
 bool             fStart;
 AnsiString       str;// User declarations
public:		// User declarations
        __fastcall TForm1(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm1 *Form1;
//---------------------------------------------------------------------------
#endif
