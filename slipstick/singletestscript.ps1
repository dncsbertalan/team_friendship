javac -d bin .\src\Constants\* .\src\Entities\* .\src\Items\* .\src\Labyrinth\* .\src\Runnable\* .\src\GameManagers\Commands\* .\src\GameManagers\CommandController.java .\src\GameManagers\Game.java .\src\GameManagers\Menu.java .\src\GameManagers\RoundManager.java

$testOutputPath = "tests/output/"
$testExpectedPath = "tests/expected/"
$testsPath = "tests/input/"

function TestSikeres($testName, $outputFile) {
    $testFile = $testsPath + $testName + ".txt"
    type $testFile | java -cp bin Runnable.Main | tee $outputFile
    $expectedFile = $testExpectedPath + $testName + "_expected.txt"
    $expectedContent = Get-Content $expectedFile
    $actualContent = Get-Content $outputFile

    Write-Host "Expected output:"
    Write-Host (Get-Content $expectedFile -Raw) -ForegroundColor DarkGray
    Write-Host "Actual output:"
    Write-Host (Get-Content $outputFile -Raw) -ForegroundColor DarkGray

    return ($actualContent -eq $expectedContent)
}

Write-Host "Type 'exit' to close"

do {
    $testName = Read-Host -Prompt "------------------------------`nName the test to run"

    if ($testName -eq "exit") {
        break
    }

    $testFile = $testsPath + $testName + ".txt"
    if (-not (Test-Path $testFile)) {
        Write-Host "No test with the name: $testName"
        continue
    }

    Write-Host "Input:"
    Write-Host (Get-Content $testFile -Raw) -ForegroundColor DarkGray

    $outputFile = $testOutputPath + $testName + "_output.txt"
    if ((TestSikeres $testName $outputFile -Raw) -eq $true) {
        Write-Host "Test $testName was successful" -ForegroundColor Green
        Write-Host "Type 'exit' to close"
    } else {
        Write-Host "Test $testName was unsuccessful" -ForegroundColor Red
        Write-Host "Type 'exit' to close"
    }

} while ($true)