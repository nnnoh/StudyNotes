## 语法

## 内置对象

[Angular的 $q, defer, promise](https://www.jianshu.com/p/ed6e9886fa76)

[Promise](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Promise)

[Angularjs中的拦截器](https://www.cnblogs.com/littlemonk/p/5512253.html)

## 示范

### 表单

#### 多选框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<script src="https://cdn.staticfile.org/angular.js/1.4.6/angular.min.js"></script>
</head>
<body>
<div ng-app="myApp" ng-controller="myCtrl" >          
	<br>
    选择
    <div ng-repeat="item in list">
        <input type="checkbox" name="tagName" ng-model="item.models" ng-checked="isSelected(item.id)" value="{{item.id}}" title ="{{item.shortName}}" ng-click="select(item.id,$event)"> {{item.shortName}}
    </div>
	<input type="button" ng-click="click()">
    结果：{{result}}
</div>
<script>    
    var app = angular.module('myApp', []);
    app.controller('myCtrl', function($scope) {
        //创建checkbox用的
        $scope.list=[{"id":1,"shortName":"张三"},{"id":2,"shortName":"李四"},{"id":3,"shortName":"王二"}];
        //存储已选
        $scope.result = [2,3];
        //触发事件
        $scope.select = function(id,event){            
            console.log(event)//打印看看这是什么，有利于理解
            console.log(action)

            var action = event.target;
            if(action.checked){//选中，就添加
                if($scope.result.indexOf(id) == -1){//不存在就添加
                    $scope.result.push(id);
                }
            }else{//去除就删除result里
                var idx = $scope.result.indexOf(id);
                if( idx != -1){//不存在就添加
                    $scope.result.splice(idx,1);
                }
            }
			
        };
		$scope.isSelected = function(id){          
            return $scope.result.indexOf(id)!=-1; 
            //只要返回的结果为true，则对应的checkbox就会被选中，
            //所以我的思路是看存添加页面的结果里是否含有当前id即value值，
            //有就返回的true，没有就返回false
        };
		$scope.click= function(){
			angular.forEach($scope.list, function (i) {
                i.models = false;
            })
			$scope.result = [];
		}
    });
</script>
</body>
</html>
```

## 其他

### 项目运行

#### 项目初始启动

1. `npm install`
2. `npm run dll`
3. `npm start`

#### 项目部署

1. `npm run build`
2. 将`dist`目录放至 tomcat wabapps 下，并重命名为项目的 context-path
