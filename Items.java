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
  int atkBonus;
  //IDEA method for equip and unequip. the equip method adds the attack bonus and the unqueip method removes it
  public void Equip(Entity user){
    user.atk += atkBonus;
  }
  public void Unequip(Entity user){
    //
  }
}
public class armor extends item(int ID,String name, int quantity) {
 int defBonus;
 int acBonus;
public void Equip(
}
public class consumable extends item(int ID,String name, int quantity) {
  
}
