package cn.edu.sjtu.zang.objpool;

public class ObjpoolTest {
  public static void main(String[] args) throws Exception {     
    ObjectPool objPool = new ObjectPool();
    objPool.createPool();     
    Object obj = objPool.getObject();     
    objPool.returnObject(obj);
    objPool.closeObjectPool();     
  }  
}
