@echo off

@REM target����javadoc.jar�Asource.jar�Ajar�t�@�C����zip���k����o�b�`�t�@�C��
@REM target����jar�t�@�C���� lib.zip �t�@�C�����ň��k����
ECHO jar�t�@�C����zip���k���܂�
powershell Compress-Archive -Path ./target/*.jar -DestinationPath common-utils4j.zip -Force
ECHO zip���k���܂���