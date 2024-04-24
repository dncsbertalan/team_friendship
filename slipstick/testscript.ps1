javac -d bin .\src\Constants\* .\src\Entities\* .\src\Items\* .\src\Labyrinth\* .\src\Runnable\* .\src\GameManagers\Commands\* .\src\GameManagers\CommandController.java .\src\GameManagers\Game.java .\src\GameManagers\Menu.java .\src\GameManagers\RoundManager.java

$testOutputPath = "tests/output/"
$testExpectedPath = "tests/expected/"
$testsPath = "tests/input/"
$testFiles = "Move.txt", "test2.txt", "test3.txt"

$totalTests = 0
$successfulTest = 0

function Test($testName, $outputFile) {
    $testFile = $testsPath + $testName + ".txt"
    
    #type $testFile | java -cp bin Runnable.Main | tee $outputFile
    Get-Content $testFile -Encoding UTF8 | java -cp bin Runnable.Main | Out-File -FilePath $outputFile -Encoding UTF8

    $expectedFile = $testExpectedPath + $testName + "_expected.txt"
    $expectedContent = Get-Content $expectedFile
    $actualContent = Get-Content $outputFile
    
    return ($actualContent -eq $expectedContent)
}

foreach ($testFile in $testFiles) {
    Write-Host "----------------------------"
    Write-Host "Running: $testFile"
    $totalTests++
    
    $testName = [System.IO.Path]::GetFileNameWithoutExtension($testFile)
    $outputFile = $testOutputPath + $testName + "_output.txt"

    $res = Test $testName $outputFile
    if ($res -eq $true) {
        Write-Host "Test $testName was successful" -ForegroundColor Green
        $successfulTest++
    } else {
        Write-Host "Test $testName was unsuccessful" -ForegroundColor Red
    }
}

Write-Host "`n============================================"
Write-Host "RESULT:	$successfulTest successful test out of $totalTests"

