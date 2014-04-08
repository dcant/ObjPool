package cn.edu.sjtu.zang.objpool;

import java.util.Enumeration;
import java.util.Vector;

public class ObjectPool {     
  private int numObjects = 10;  
  private Vector<PooledObject> objects = null;       
 
  public ObjectPool() {            
  }     
   
  public synchronized void createPool(){
      if (objects != null) {     
          return;
      }     
      
      objects = new Vector<PooledObject>();     

      for (int x = 0; x < numObjects; x++) {
            Object obj = new Object();     
            objects.addElement(new PooledObject(obj));               
      }
  }     
   
  public synchronized Object getObject(){
      if (objects == null) {     
          return null;    
      }     
   
      Object get = getFreeObject();   
    
      while (get == null) {     
          wait(250);     
          get = getFreeObject();
      }     
   
      return get;
  }     
     
  private Object getFreeObject(){       
      return findFreeObject();     
  }     
  
  private Object findFreeObject(){     
   
      Object obj = null;     
      PooledObject pObj = null;     

      Enumeration<PooledObject> enumerate = objects.elements();     

      while (enumerate.hasMoreElements()) {     
          pObj = (PooledObject) enumerate.nextElement();     

          if (!pObj.isBusy()) {     
              obj = pObj.getObject();     
              pObj.setBusy(true);     
          }
      }
  
      return obj;
  }
   
  public void returnObject(Object obj) {
      if (objects == null) {     
          return;     
      }     
   
      PooledObject pObj = null;     
   
      Enumeration<PooledObject> enumerate = objects.elements();
      
      while (enumerate.hasMoreElements()) {     
          pObj = (PooledObject) enumerate.nextElement();     
  
          if (obj == pObj.getObject()) {
              pObj.setBusy(false);     
              break;     
          }     
      }     
  }     
 
  public synchronized void closeObjectPool() {
      if (objects == null) {     
          return;     
      }     
   
      PooledObject pObj = null;     
   
      Enumeration<PooledObject> enumerate = objects.elements();     
   
      while (enumerate.hasMoreElements()) {     
   
          pObj = (PooledObject) enumerate.nextElement();     

          if (pObj.isBusy()) {     
              wait(5000);
          }     
 
          objects.removeElement(pObj);     
      }     

      objects = null;     
  }     
  
  private void wait(int mSeconds) {     
      try {     
          Thread.sleep(mSeconds);     
      }
     catch (InterruptedException e) {     
      }     
  }     
  
  class PooledObject {     
   
      Object objection = null;  
      boolean busy = false;     
 
      public PooledObject(Object objection) {     
   
          this.objection = objection;     
   
      }     
   
      public Object getObject() {     
          return objection;     
      }     

      public void setObject(Object objection) {     
          this.objection = objection;     
   
      }     

      public boolean isBusy() {     
          return busy;     
      }     

      public void setBusy(boolean busy) {     
          this.busy = busy;     
      }     
  }     
}