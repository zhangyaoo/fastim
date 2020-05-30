package com.zyblue.fastim.logic.constant;

public enum DelayTaskType {
    PUSH_TASK((byte) 0, "pushReportTask",  "极光推送查询任务"),
    TEST_TASK((byte) 1, "testTask", "测试任务");

    private Byte value;

    private String beanName;

    private String description;

    DelayTaskType(Byte value, String beanName, String description){
        this.value = value;
        this.beanName = beanName;
        this.description = description;
    }

    public static DelayTaskType getTypeByValue(int taskType) {
        for (DelayTaskType type : DelayTaskType.values()) {
            if (taskType == type.getValue().intValue()) {
                return type;
            }
        }
        return null;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
