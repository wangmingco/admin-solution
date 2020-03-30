<template>
    <div class="table-container">
            <el-table
                ref="multipleTable"
                :data="tableData"
                style="width: 100%"
                border
                fit
                highlight-current-row
                @selection-change="handleSelectionChange"
                :default-sort = "{prop: 'date', order: 'descending'}"
                >

                <el-table-column
                  type="selection"
                  width="55">
                </el-table-column>

                <el-table-column
                  v-for="{ prop, label } in titles"
                  :key="prop"
                  :prop="prop"
                  :label="label"
                  header-align = "center"
                  sortable
                  show-overflow-tooltip
                  >
                </el-table-column>

                 <el-table-column align="center" label="Actions" width="125" class-name="small-padding fixed-width" fixed="right">
                  <template slot-scope="scope">
                    <el-button  type="warning" size="mini" icon="el-icon-edit" @click="edit(scope.row)"></el-button>
                    <el-button  type="danger" size="mini" icon="el-icon-delete" @click="deleteOne(scope.row)"></el-button>
                  </template>
                </el-table-column>

            </el-table>
            
            <div style="margin-top: 20px">
              <el-button @click="toggleSelection(tableData)">全选</el-button>
              <el-button @click="toggleSelection()">取消选择</el-button>
            </div>
            
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 50, 100, 200, 300]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalNum">
            </el-pagination>
    </div>

</template>

<script>

export default {
    name: 'DataQuery',
    data() {
        return {
            addRow: [],
            editRow: [],
            currentPage: 1,
            pageSize: 10,
            totalNum: 0,
        }
    },
    props: {
        titles: [],
        tableData: []
    },  
    methods: {
        edit(row) {
            this.editRow = []
            for(var key in row) {
                var newRow = {
                    column: key,
                    value: row[key]
                }
                this.editRow.push(newRow)
            }
            this.updateDialogVisible = true
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            const jsonStr = JSON.stringify(this.multipleSelection, null, 2)
            console.info("select : " + jsonStr)
        },
        toggleSelection(rows) {
            if (rows) {
            rows.forEach(row => {
                this.$refs.multipleTable.toggleRowSelection(row);
            });
            } else {
            this.$refs.multipleTable.clearSelection();
            }
        },
        deleteOne(row) {
            let query = {
                datasource: this.datasource,
                tableName : this.tableName,
                data: JSON.stringify(row)
            }
            httpPost(query, '/mysql/deleteOne').then(res=> {
                this.request()
                this.$notify({
                    title: '删除完成',
                    type: 'success',
                    message: "结果:" + res.data,
                    duration: 5000
                });
            }).catch(err => {
                this.$message.error("request error /mysql/deleteOne  " + err)
                console.error(err)
            })
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageSize = val
            this.request();
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.currentPage = val
            this.request();
        },

    }
}
</script>

