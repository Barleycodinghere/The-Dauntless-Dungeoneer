import java.util.*

public abstract class item(){
  private int itemId;
  private int quantity;
  private String name;
}
public abstract void Equip(Entity user);
public abstract void Unequip(Entity user);

public item(int itemId, String name, int quantity){
  this.itemId = itemId;
  this.quantity = quantity;
  this.name = name;
  
}

public class weapon extends item(int itemId, String name, int quantity) {
  //int atkPlus;
  private int atkBonus;
  //IDEA method for equip and unequip. the equip method adds the attack bonus and the unqueip method removes it
  @Override public void Equip(Entity user){
    user.atk += atkBonus;
  }
  @Override public void Unequip(Entity user){
   //
  }
}
public class armor extends item(int itemId,String name, int quantity) {
  private int defBonus;
  private int acBonus;
public void Equip(
}
public class consumable extends item(int itemId,String name, int quantity) {
  
}
