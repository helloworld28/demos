package com.jim.java8;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 限行违法计算
 *
 * @author Jim
 * @date 2019/2/26
 */
public class ComputeRestrict {

    /**
     * 开车记录
     */
    List<String> driverRows = new ArrayList<>();

    public ComputeRestrict() {
        initDemoDriveData();
    }

    /**
     * 初始化开车记录
     */
    private void initDemoDriveData() {
        driverRows.add("2019-02-01");
        driverRows.add("2019-02-07");
        driverRows.add("2019-02-08");
        driverRows.add("2019-02-09");
        driverRows.add("2019-02-12");
    }

    /**
     * 是否违法
     *
     * @param localDate
     * @return
     */
    private boolean isIllegal(LocalDate localDate) {
        return !hasContinuousNotDrive4Day(localDate, 8, 0);

    }

    /**
     * 是否存在连续停的四天
     * 判断某天是否
     *
     * @param fromDate     　开始计算时间
     * @param leftDays     　余下天数
     * @param notDriveDays 　累计停的天数
     * @return　true/存在
     */
    private boolean hasContinuousNotDrive4Day(LocalDate fromDate, int leftDays, int notDriveDays) {
        for (int i = 1; i < leftDays; i++) {
            LocalDate localDate = fromDate.plusDays(-i);
            if (isNotDrive(localDate)) {
                ++notDriveDays;
                if (notDriveDays >= 4) {
                    return true;
                }
            } else {
                if ((leftDays - i) >= 4) {
                    return false;
                } else {
                    return hasContinuousNotDrive4Day(localDate, leftDays - i, 0);
                }

            }

        }
        return false;
    }

    private boolean isNotDrive(LocalDate tempDate) {
        return !driverRows.contains(tempDate.toString());
    }


    public static void main(String[] args) {
        LocalDate localDate = LocalDate.parse("2019-02-14");
        System.out.println("本次开车是否违法：" + new ComputeRestrict().isIllegal(localDate));

    }
}
