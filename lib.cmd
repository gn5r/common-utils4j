@echo off

@REM target下のjavadoc.jar、source.jar、jarファイルをzip圧縮するバッチファイル
@REM target下のjarファイルを lib.zip ファイル名で圧縮する
ECHO jarファイルをzip圧縮します
powershell Compress-Archive -Path ./target/*.jar -DestinationPath lib.zip -Force
ECHO zip圧縮しました