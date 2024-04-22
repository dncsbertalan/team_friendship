$testOutputPath = "tests/output/"
$testExpectedPath = "tests/expected/"
$testsPath = "tests/input/"
$testFiles = "test1.txt", "test2.txt"

$totalTests = 0
$successfulTest = 0

foreach ($testFile in $testFiles) {
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
        Write-Host "$test_name was unsuccessful"
    } else {
        Write-Host "$test_name was successful"
	$successfulTest++
    }
}

Write-Host "$successfulTest successful test out of $totalTests"

