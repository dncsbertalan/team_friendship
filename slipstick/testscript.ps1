$javaProgram = "java -cp . Main"
$testFiles = "test1.txt", "test2.txt"

foreach ($testFile in $testFiles) {
    Write-Host "Futtatás: $testFile"
    
$inputContent = Get-Content $testFile -Raw
    # Tesztfajl beolvasasa a Java programnak bemenetkent
    $outputFile = "$testFile_output.txt"
    & $javaProgram < $testFile > $outputFile
    #& cmd /c "$javaProgram" <<< $inputContent | Out-File -FilePath $outputFile -Encoding utf8


    # Ellenorzes: kimeneti fajl osszehasonlitasa az elvart eredmennyel
    #	$expectedFile = "$testFile_expected.txt"
    #	if (Compare-Object (Get-Content $outputFile) (Get-Content $expectedFile)) {
    #	    Write-Host "Sikertelen teszt: $testFile"
    #	} else {
    #	    Write-Host "Sikeres teszt: $testFile"
    #	}
}