# Wait for the Spring Boot server to become active
Write-Host "Waiting for Spring Boot backend to start on http://localhost:8080..." -ForegroundColor Cyan
$started = $false
for ($i = 1; $i -le 30; $i++) {
    try {
        $resp = Invoke-WebRequest -Uri "http://localhost:8080/v3/api-docs" -Method Get -TimeoutSec 2 -UseBasicParsing
        if ($resp.StatusCode -eq 200) {
            $started = $true
            break
        }
    } catch {
        # ignore and wait
    }
    Start-Sleep -Seconds 2
}

if (-not $started) {
    Write-Error "Spring Boot did not start in time."
    exit 1
}

# 1. Login as Admin
Write-Host "`n--- Step 1: Login as Admin ---" -ForegroundColor Yellow
$loginBody = @{
    email = "admin@gmail.com"
    password = "admin123"
} | ConvertTo-Json

$headers = @{
    "Content-Type" = "application/json"
}

$loginResp = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method Post -Body $loginBody -Headers $headers
$token = $loginResp.accessToken
$authHeaders = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}
Write-Host "Admin Login Successful!" -ForegroundColor Green

# 2. Register bugingoderer@gmail.com as User
Write-Host "`n--- Step 2: Register bugingoderer@gmail.com ---" -ForegroundColor Yellow
$regBody = @{
    email = "bugingoderer@gmail.com"
    password = "password123"
    role = "EMPLOYEE"
} | ConvertTo-Json

try {
    $regResp = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/register" -Method Post -Body $regBody -Headers $headers
    Write-Host "Registration response: $regResp" -ForegroundColor Green
} catch {
    Write-Host "Registration warning (user might already exist): $_" -ForegroundColor Yellow
}

# 3. Create Employee entity for bugingoderer@gmail.com
Write-Host "`n--- Step 3: Create Employee Entity for bugingoderer@gmail.com ---" -ForegroundColor Yellow
$empBody = @{
    firstName = "Bugingo"
    lastName = "Derer"
    email = "bugingoderer@gmail.com"
    district = "Gasabo"
    mobile = "+250788888888"
    dateOfBirth = "1990-01-01"
    department = "IT"
    position = "Developer"
    baseSalary = 80000.0
    status = "ACTIVE"
    joiningDate = "2024-01-01"
} | ConvertTo-Json

try {
    $empResp = Invoke-RestMethod -Uri "http://localhost:8080/api/employees" -Method Post -Body $empBody -Headers $authHeaders
    Write-Host "Employee created: $($empResp.firstName) $($empResp.lastName) (ID: $($empResp.employeeId))" -ForegroundColor Green
} catch {
    Write-Host "Employee creation failed (might already exist): $_" -ForegroundColor Yellow
}

# 4. Generate Payroll Draft for July 2026
Write-Host "`n--- Step 4: Generate July 2026 Payroll Draft ---" -ForegroundColor Yellow
try {
    $genResp = Invoke-RestMethod -Uri "http://localhost:8080/api/payrolls/generate?month=July&year=2026" -Method Post -Headers $authHeaders
    Write-Host "Payroll generated successfully for active employees." -ForegroundColor Green
} catch {
    Write-Host "Payroll generation failed/already draft exists: $_" -ForegroundColor Yellow
}

# 5. Approve Payroll for July 2026 (Triggers SP cursor insert and email)
Write-Host "`n--- Step 5: Approve July 2026 Payroll ---" -ForegroundColor Yellow
try {
    $approveResp = Invoke-RestMethod -Uri "http://localhost:8080/api/payrolls/approve?month=July&year=2026&institution=RRA" -Method Post -Headers $authHeaders
    Write-Host "Approval Response: $approveResp" -ForegroundColor Green
} catch {
    Write-Host "Approval failed: $_" -ForegroundColor Red
}

# 6. Retrieve messages from DB
Write-Host "`n--- Step 6: Verify Database Cursor messages ---" -ForegroundColor Yellow
try {
    $msgResp = Invoke-RestMethod -Uri "http://localhost:8080/api/messages" -Method Get -Headers $authHeaders
    $relevantMsgs = $msgResp | Where-Object { $_.messageText -like "*bugingoderer@gmail.com*" -or $_.messageText -like "*Bugingo*" }
    Write-Host "Cursor messages matching Bugingo in DB:" -ForegroundColor Green
    $relevantMsgs | Format-Table -Property id, messageText, sentAt
} catch {
    Write-Host "Failed to query messages: $_" -ForegroundColor Red
}
