public class FoodManage extends Restaurant{

    private String foodName;
    private double price;

    public FoodManage(String name, double price){
        this.foodName = name;
        this.price = price;
    }
    public void setFoofName(String name){
        this.foodName = name;
    }

    public void setprice(int price){
        this.price = price;
    }
    public String getFoodName() {
        return foodName;
    }
    public double getPrice() {
        return price;
    }


}
