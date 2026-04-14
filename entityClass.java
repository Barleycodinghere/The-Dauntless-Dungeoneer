import java.util.Random;

public abstract class Entity {
    protected String name;
    protected int hp;
    protected int mana;
    protected int ac;
    protected int speed;
    protected int atk;
    protected int def;
    protected int damage;

    public Entity(String name, int hp, int mana, int ac, int speed, int atk, int def, int damage){
        this.name = name;
        this.hp = hp;
        this.mana = mana;
        this.ac = ac;
        this.speed = speed;
        this.atk = atk;
        this.def = def;
        this.damage = damage;
    }

    public void basicAttack(Entity target){
        if(this.atk + rollD20() > target.def + target.ac)
            target.takeDamage(this.damage);
        else
            System.out.println("Miss!");
    }

    public void takeDamage(int damage){
        this.hp -= damage;
        System.out.println(this.name + " now has " + this.hp + " HP left");
    }

    public static int rollD20(){ return new Random().nextInt(20) + 1; }
}

public abstract class PlayerCharacter extends Entity{
    public PlayerCharacter(String name, int hp, int mana, int ac, int speed, int atk, int def, int damage){
        super(name, hp, mana, ac, speed, atk, def, damage);
    }

    public abstract void ability_num1(Entity target);
    public abstract void ability_num2(Entity target);
    public abstract void ability_num3(Entity target);
}

public class Cleric extends playerCharacter {
    private static int baseHp = 80;
    private static int baseMana = 100;
    private static int baseAc = 10;
    private static int baseSpeed = 2;
    private static int baseAtk = 6;
    private static int baseDef = 6;
    private static int baseDamage = 15;

    public Cleric(String name){
        super(name, baseHp, baseMana, baseAc, baseSpeed, baseAtk, baseDef, baseDamage);
    }

    //subject to change

    @Override public void ability_num1(Entity target){
        //healing word
    }

    @Override public void ability_num2(Entity target){
        //bless
    }

    @Override public void ability_num3(Entity target){
        //guiding bolt
    }
}

public class Warrior extends playerCharacter {
    private static int baseHp = 120;
    private static int baseMana = 50;
    private static int baseAc = 10;
    private static int baseSpeed = 1;
    private static int baseAtk = 8;
    private static int baseDef = 8;
    private static int baseDamage = 20;

    public Warrior(String name) {
        super(name, baseHp, baseMana, baseAc, baseSpeed, baseAtk, baseDef, baseDamage);
    }

    //subject to change
    @Override
    public void ability_num1(Entity target) {
        System.out.println(this.name + " uses Shield Bash!");
        // Logic: damage + stun target
    }

    @Override
    public void ability_num2(Entity target) {
        System.out.println(this.name + " uses Battle Cry!");
        // Logic: buff this.atk
    }

    @Override
    public void ability_num3(Entity target) {
        System.out.println(this.name + " uses Execute!");
        // Logic: massive damage if target HP is low
    }
}

public class Mage extends playerCharacter {
    private static int baseHp = 50;
    private static int baseMana = 200;
    private static int baseAc = 10;
    private static int baseSpeed = 4;
    private static int baseAtk = 10;
    private static int baseDef = 4;
    private static int baseDamage = 25;

    public Mage(String name) {
        super(name, baseHp, baseMana, baseAc, baseSpeed, baseAtk, baseDef, baseDamage);
    }

    //subject to change
    @Override
    public void ability_num1(Entity target) {
        System.out.println(this.name + " casts Fireball!");
        // Logic: High AoE or single target damage
    }

    @Override
    public void ability_num2(Entity target) {
        System.out.println(this.name + " casts Frost Nova!");
        // Logic: Lower target speed or freeze them
    }

    @Override
    public void ability_num3(Entity target) {
        System.out.println(this.name + " casts Arcane Intellect!");
        // Logic: Buff mana or damage for self/ally
    }
}