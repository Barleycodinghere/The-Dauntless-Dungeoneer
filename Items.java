import java.util.*

public abstract item(){
  private int ID;
  private int quantity;
  private String name;
}
public item(int ID, String name, int quantity){
  this.ID = ID;
  this.quantity = quantity;
  this.name = name;
  
}

public class weapon extends item(int ID, String name, int quantity) {
  //int atkPlus;
  int atk;
  public void Attack(Entity user){
    user.attack
  }
}
public class armor extends item(int ID,String name, int quantity) {
 //int defPlus;
 //int acPlus;
}
public class consumable extends item(int ID,String name, int quantity) {
  
}
