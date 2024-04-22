$testOutputPath = "tests/output/"
$testExpectedPath = "tests/expected/"
$testsPath = "tests/input/"
$testFiles = "move.txt", "test2.txt"

$totalTests = 0
$successfulTest = 0

foreach ($testFile in $testFiles) {
    Write-Host "----------------------------"
    Write-Host "Running: $testFile"
    $totalTests++
    
    # Tesztfajl beolvasasa
    $test_name = [System.IO.Path]::GetFileNameWithoutExtension($testFile)
    $outputFile = $testOutputPath + $test_name + "_output.txt"
    type $testsPath$testFile | java -cp bin Runnable.Main | tee $outputFile

    # Ellenorzes
    $expectedFile = $testExpectedPath + $test_name + "_expected.txt"
    #Write-Host "$expectedFile" # path check

    if (Compare-Object (Get-Content $outputFile) (Get-Content $expectedFile)) {
        Write-Host "Test $test_name was unsuccessful"
    } else {
        Write-Host "Test $test_name was successful"
	$successfulTest++
    }
}

Write-Host ""
Write-Host "============================================"
Write-Host "RESULT:	$successfulTest successful test out of $totalTests"

