package parking;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InOrderParkingStrategyTest {

  private static InOrderParkingStrategy inOrderParkingStrategy;

  @Before
  public void setUp() {
    inOrderParkingStrategy = new InOrderParkingStrategy();
  }

  @Test
  public void
      testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {
    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
     * With using Mockito to mock the input parameter */
    // given
    Car car = createMockCar("粤C88888");
    ParkingLot parkingLot = createMockParkingLot("Southern Software Parking Lot", 20);
    List<ParkingLot> lots = Collections.singletonList(parkingLot);
    // when
    Receipt receipt = inOrderParkingStrategy.park(lots, car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("Southern Software Parking Lot", receipt.getParkingLotName());
  }

  @Test
  public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {
    /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
     * With using Mockito to mock the input parameter */
    // given
    Car car = createMockCar("粤C88888");
    // when
    Receipt receipt = inOrderParkingStrategy.park(null, car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("No Parking Lot", receipt.getParkingLotName());
  }

  @Test
  public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt() {

    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
    // given
    Car car = createMockCar("粤C88888");
    ParkingLot parkingLot = createMockParkingLot("Southern Software Parking Lot", 20);
    when(parkingLot.isFull()).thenReturn(true);
    InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
    // when
    Receipt receipt = inOrderParkingStrategy.park(Collections.singletonList(parkingLot), car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("No Parking Lot", receipt.getParkingLotName());
    verify(inOrderParkingStrategy, times(1)).park(anyList(), any(Car.class));
  }

  @Test
  public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt() {
    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
    // given
    Car car = createMockCar("粤C88888");
    ParkingLot parkingLot = createMockParkingLot("Southern Software Parking Lot", 20);
    InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
    // when
    Receipt receipt = inOrderParkingStrategy.park(Collections.singletonList(parkingLot), car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("Southern Software Parking Lot", receipt.getParkingLotName());
    verify(inOrderParkingStrategy, times(1)).park(anyList(), any(Car.class));
  }

  @Test
  public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt() {
    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
    // given
    Car car = createMockCar("粤C88888");
    ParkingLot parkingLot = createMockParkingLot("Southern Software Parking Lot", 20);
    when(parkingLot.isFull()).thenReturn(true);
    InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
    // when
    Receipt receipt = inOrderParkingStrategy.park(Collections.singletonList(parkingLot), car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("No Parking Lot", receipt.getParkingLotName());
    verify(inOrderParkingStrategy, times(1)).park(anyList(), any(Car.class));
  }

  @Test
  public void
      testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot() {
    /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
    // given
    Car car = createMockCar("粤C88888");
    ParkingLot parkingLot1 = createMockParkingLot("Southern Software Parking Lot", 20);
    when(parkingLot1.isFull()).thenReturn(true);
    ParkingLot parkingLot2 = createMockParkingLot("Southern Software Parking Lot 2", 20);
    InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
    // when
    Receipt receipt = inOrderParkingStrategy.park(Arrays.asList(parkingLot1, parkingLot2), car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("Southern Software Parking Lot 2", receipt.getParkingLotName());
    verify(inOrderParkingStrategy, times(1)).park(anyList(), any(Car.class));
  }

  private Car createMockCar(String carName) {
    Car car = mock(Car.class);
    when(car.getName()).thenReturn(carName);
    return car;
  }

  private ParkingLot createMockParkingLot(String parkingLotName, int capacity) {
    ParkingLot parkingLot = mock(ParkingLot.class);
    when(parkingLot.getName()).thenReturn(parkingLotName);
    when(parkingLot.getMaxCapacity()).thenReturn(capacity);
    return parkingLot;
  }
}
