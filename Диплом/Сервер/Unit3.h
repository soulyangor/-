//---------------------------------------------------------------------------

#ifndef Unit3H
#define Unit3H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ADODB.hpp>
#include <DB.hpp>
#include <DBCtrls.hpp>
#include <DBGrids.hpp>
#include <ExtCtrls.hpp>
#include <Grids.hpp>
//---------------------------------------------------------------------------
class TForm3 : public TForm
{
__published:	// IDE-managed Components
        TADOTable *ADOTable1;
        TDBGrid *DBGrid1;
        TDBNavigator *DBNavigator1;
        TADOTable *ADOTable2;
        TDBGrid *DBGrid2;
        TDataSource *DataSource1;
        TDataSource *DataSource2;
        TDBGrid *DBGrid3;
        TDBNavigator *DBNavigator2;
        TDBNavigator *DBNavigator3;
        TADOQuery *ADOQuery1;
        TDataSource *DataSource3;
        TDBGrid *DBGrid4;
        TDBNavigator *DBNavigator4;
        TADOQuery *ADOQuery2;
        TDataSource *DataSource4;
        TDBGrid *DBGrid5;
        TDBNavigator *DBNavigator5;
        TLabel *Label1;
        TLabel *Label2;
        TLabel *Label3;
        TLabel *Label4;
        TLabel *Label5;
        TButton *Button1;
        TADOQuery *ADOQuery3;
        TButton *Button2;
        TButton *Button3;
        TButton *Button4;
        TButton *Button5;
        TButton *Button6;
        TEdit *Edit2;
        TEdit *Edit3;
        TButton *Button7;
        TEdit *Edit4;
        TDBComboBox *DBComboBox1;
        TDBGrid *DBGrid6;
        TDBNavigator *DBNavigator6;
        TDataSource *DataSource5;
        TADOTable *ADOTable3;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall Button2Click(TObject *Sender);
        void __fastcall Button3Click(TObject *Sender);
        void __fastcall Button4Click(TObject *Sender);
        void __fastcall Button5Click(TObject *Sender);
        void __fastcall Button6Click(TObject *Sender);
        void __fastcall Button7Click(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TForm3(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm3 *Form3;
//---------------------------------------------------------------------------
#endif
