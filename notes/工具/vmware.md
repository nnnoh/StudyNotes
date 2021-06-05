### 网络配置

- 配置好网络后，还是无法 ping 通时，确认相关服务是否启动。如，VMware NAT Service。

### 共享文件夹

1. 虚拟机安装VMware tools。

2. 虚拟机设置选项中添加共享文件夹。

3. centos 7 需要安装 open-vm-tools。

    ```bash 
    yum install open-vm-tools
    ```

4. 查看共享文件夹目录名。

   ```bash
   vmware-hgfsclient
   ```
   
5. 挂载共享目录，如下普通用户也能使用共享目录。

   ```bash 
   vmhgfs-fuse .host:/[共享目录名称，可选] /mnt/hgfs -o allow_other
   ```

