
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CurrencyTest {

    private final static String USD = "USD";
    private final static Map<String,BigDecimal> TOTAL_AMOUNT = new HashMap<String,BigDecimal>();
    private final static Map<String,Double> CURRENCY_EXCHANGE_RATE = new HashMap<String,Double>();
    private final static Map<String,String> SHOW_OUTPUTS = new HashMap<String,String>();
    static{
        CURRENCY_EXCHANGE_RATE.put("USD",1.0000);
        CURRENCY_EXCHANGE_RATE.put("HKD",7.7509);
        CURRENCY_EXCHANGE_RATE.put("CNY",7.0155);
    }

    public static void main(String[] args) {
        ArrayList<String> inputs = getInputs();
        getOutputs(inputs);
        System.out.println("Sample outputs:");

        showOutputs(TOTAL_AMOUNT);
        for (Map.Entry<String, String> entry: SHOW_OUTPUTS.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }

    }

    public static ArrayList<String> getInputs(){
        ArrayList<String> originalInputs = new ArrayList<String>();
        BufferedReader br = null;
        Reader reader = null;
        try {
            reader = new FileReader("D:/payment.txt");
            br = new BufferedReader(reader);
            String line = "";
            System.out.println("Sample inputs");
            while(null != (line=br.readLine())){
                System.out.println(line);
                originalInputs.add(line);
            }
        }catch (Exception e){

        }finally {
            try {
               if(null!=br){
                   br.close();
                }
            }catch (Exception e){
            }
            try {
                if(null!=reader){
                    reader.close();
                }
            }catch (Exception e){
            }
        }
        return originalInputs;
    }

    public static void getOutputs (ArrayList<String> inputs ){
        ArrayList<String> outputs = new ArrayList<>(inputs.size());
        for (String str:inputs ) {
            String[] arr = str.split(" ");

            dealWithUSDCurrency(arr[0],new BigDecimal(arr[1]));
        }
    }

    public static void dealWithUSDCurrency(String currencyType, BigDecimal amount){

        if(!TOTAL_AMOUNT.containsKey(currencyType)){
            TOTAL_AMOUNT.put(currencyType,amount);
        }else{
            TOTAL_AMOUNT.put(currencyType,TOTAL_AMOUNT.get(currencyType).add(amount));
        }
    }

    public static void showOutputs(Map<String,BigDecimal> map){
        for (Map.Entry<String, BigDecimal> entry: map.entrySet()) {
           if(!USD.equals(entry.getKey())){
               BigDecimal usdAmount = new BigDecimal(String.valueOf(entry.getValue())).divide(new BigDecimal(CURRENCY_EXCHANGE_RATE.get(entry.getKey())), 2,BigDecimal.ROUND_HALF_UP);
               SHOW_OUTPUTS.put(entry.getKey().toString(),entry.getValue()+"(USD "+usdAmount+")");
           }else{
               SHOW_OUTPUTS.put(entry.getKey().toString(),entry.getValue().toString());
           }
        }
    }
}
