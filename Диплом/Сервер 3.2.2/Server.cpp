//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
//---------------------------------------------------------------------------
USEFORM("Unit1.cpp", Form1);
USEFORM("..\������ 3.2.1\Unit2.cpp", Form2);
USEFORM("..\������ 3.2.1\Unit3.cpp", Form3);
USEFORM("..\������ 3.2.1\Unit4.cpp", Form4);
USEFORM("..\������ 3.2.1\Unit5.cpp", Form5);
USEFORM("..\������ 3.2.1\Unit6.cpp", Form6);
USEFORM("..\������ 3.2.1\Unit7.cpp", PasswordDlg);
USEFORM("..\������ 3.2.1\Unit8.cpp", Form8);
USEFORM("..\������ 3.2.1\Unit9.cpp", Form9);
//---------------------------------------------------------------------------
WINAPI WinMain(HINSTANCE, HINSTANCE, LPSTR, int)
{
        try
        {
                 Application->Initialize();
                 Application->CreateForm(__classid(TForm8), &Form8);
                 Application->CreateForm(__classid(TPasswordDlg), &PasswordDlg);
                 Application->CreateForm(__classid(TForm1), &Form1);
                 Application->CreateForm(__classid(TForm2), &Form2);
                 Application->CreateForm(__classid(TForm3), &Form3);
                 Application->CreateForm(__classid(TForm4), &Form4);
                 Application->CreateForm(__classid(TForm5), &Form5);
                 Application->CreateForm(__classid(TForm6), &Form6);
                 Application->CreateForm(__classid(TForm9), &Form9);
                 Application->Run();
        }
        catch (Exception &exception)
        {
                 Application->ShowException(&exception);
        }
        catch (...)
        {
                 try
                 {
                         throw Exception("");
                 }
                 catch (Exception &exception)
                 {
                         Application->ShowException(&exception);
                 }
        }
        return 0;
}
//---------------------------------------------------------------------------
