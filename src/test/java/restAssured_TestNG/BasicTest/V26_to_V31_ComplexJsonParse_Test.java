package restAssured_TestNG.BasicTest;

import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class V26_to_V31_ComplexJsonParse_Test {

	public static void main(String[] args) {
		// TO deal with complex Nested Json response

		JsonPath js = new JsonPath(payload.coursesPrice());
		
		// Print number of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println("The total courses are :" +count);
		
		// Print purchase amount
		int totalPurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("The total purchase amount is :" +totalPurchaseAmount);
		
		//Print title of the first course
		String firstCourseTitle = js.get("courses[0].title");
		System.out.println("The first course title is :" +firstCourseTitle);
		
		// Print all courses title and price
		for(int i=0;i<count;i++) {
			String courseTitle = js.get("courses["+i+"].title");
			System.out.println(courseTitle);
			
			System.out.println(js.get("courses["+i+"].price").toString());
			
		}
		
		// Print no. of copies sold by RPA
		for(int i=0; i<count; i++) {
			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				System.out.println(js.getInt("courses["+i+"].copies"));
				break;
			}
		}
		
		//Verify Sum of all courses price matches with purchase amt
		int sum = 0;
		for(int i=0; i<count; i++) {
		int price = js.getInt("courses["+i+"].price");
		int copies = js.getInt("courses["+i+"].copies");
		int amount = price * copies;
		sum = sum + amount;
		}
		Assert.assertEquals(sum, totalPurchaseAmount);
		
		System.out.println("Test Complete!!!");
	}

}
