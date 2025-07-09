package entities;

public class EmployeeLeaveRequest {
    private int requestId;
    private int employeeId;
    private String employeeName;
    private String leaveType;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;

    public EmployeeLeaveRequest(int requestId, int employeeId, String employeeName,
                                String leaveType, String startDate, String endDate,
                                String reason, String status) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }
}
