package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.user;
import io.restassured.response.Response;

public class userTest {

	Faker faker;
	user userPayload;

	@BeforeClass
	public void setupdata() {

		faker=new Faker();
		userPayload=new user();


		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());




	}
	@Test(priority = 1)
	public void testPostUser() {
	Response resp=UserEndPoints.createUser(userPayload);
		resp.then().log().all();

		Assert.assertEquals(resp.getStatusCode(), 200);
	}
	@Test(priority = 2)
	public void testGetUserByName() {

	Response resp=	UserEndPoints.readUser(this.userPayload.getUsername());
		resp.then().log().all();
		Assert.assertEquals(resp.getStatusCode(), 200);
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {

		//update data using payload

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

	Response resp=	UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		resp.then().log().all();
		Assert.assertEquals(resp.getStatusCode(), 200);

		//Check data after update...

	Response respafterUpdate=	UserEndPoints.readUser(this.userPayload.getUsername());
		respafterUpdate.then().log().all();
		Assert.assertEquals(respafterUpdate.getStatusCode(), 200);

	}
	@Test(priority = 4)
	public void testDeleteUserByName() {

	Response resp=	UserEndPoints.deleteUser(this.userPayload.getUsername());
		resp.then().log().all();
		Assert.assertEquals(resp.getStatusCode(), 200);

	}

}
