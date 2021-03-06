
MERGE INTO APP_CONSTANTS AS DST  
USING (SELECT * FROM (VALUES ('Szymon Czaja')) AS T1) AS SOURCE (NAME)  
ON DST.NAME = SOURCE.NAME  
WHEN MATCHED THEN  
	UPDATE SET DST.NAME = SOURCE.NAME
WHEN NOT MATCHED THEN  
	INSERT (NAME) VALUES (NAME);