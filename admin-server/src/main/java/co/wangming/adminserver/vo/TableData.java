package co.wangming.adminserver.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created By WangMing On 2020-03-27
 **/
public class TableData {

    private List<Map<String, Object>> dataList = new ArrayList<>();

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public <T> void setDataList(List<T> dataList, Function<T, Map<String, Object>> mapper) {
        List<Map<String, Object>> list = dataList.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T> TableData build(List<T> dataList, Function<T, Map<String, Object>> mapper) {
        TableData tableData = new TableData();

        if (dataList != null) {
            List<Map<String, Object>> list = dataList.stream().map(mapper).collect(Collectors.toList());
            tableData.setDataList(list);
        }

        return tableData;
    }
}
