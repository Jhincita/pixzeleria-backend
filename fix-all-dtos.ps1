# save as fix-all-dtos.ps1
$projectRoot = "."

# Function to add getters/setters to a class
function Add-GettersSetters($filePath) {
    $content = Get-Content $filePath -Raw

    # Find all private fields
    $pattern = 'private\s+(\w+)\s+(\w+);'
    $matches = [regex]::Matches($content, $pattern)

    $gettersSetters = ""
    foreach ($match in $matches) {
        $type = $match.Groups[1].Value
        $name = $match.Groups[2].Value
        $capitalizedName = $name.Substring(0,1).ToUpper() + $name.Substring(1)

        $gettersSetters += @"

    public $type get$capitalizedName`() { return $name; }
    public void set$capitalizedName`($type $name) { this.$name = $name; }
"@
    }

    # Insert before the closing brace
    $content = $content -replace '\}\s*$', "$gettersSetters`n}"
    $content | Set-Content $filePath -Encoding UTF8
}

# Fix all DTOs
$dtoFiles = Get-ChildItem -Path "$projectRoot/src/main/java/com/pixzeleria/pixzeleria/dto" -Filter "*.java" -Recurse
foreach ($file in $dtoFiles) {
    Write-Host "Fixing $($file.Name)..." -ForegroundColor Yellow
    Add-GettersSetters $file.FullName
}

Write-Host "All DTOs fixed with manual getters/setters!" -ForegroundColor Green