package parking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VipParkingStrategyTest {

  @Mock CarDao carDao;
  @InjectMocks VipParkingStrategy vipParkingStrategy;

  @Test
  public void
      testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {
    /* Exercise 4, Write a test case on VipParkingStrategy.park()
     * With using Mockito spy, verify and doReturn */
    // given
    Car car = createMockCar("粤C88888");
    ParkingLot parkingLot = createMockParkingLot("Southern Software Parking Lot", 20);
    when(parkingLot.isFull()).thenReturn(true);
    VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
    doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);
    // when
    Receipt receipt = vipParkingStrategy.park(Collections.singletonList(parkingLot), car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("Southern Software Parking Lot", receipt.getParkingLotName());
    verify(vipParkingStrategy, times(1)).park(anyList(), any(Car.class));
  }

  @Test
  public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

    /* Exercise 4, Write a test case on VipParkingStrategy.park()
     * With using Mockito spy, verify and doReturn */
    // given
    Car car = createMockCar("粤C88888");
    ParkingLot parkingLot = createMockParkingLot("Southern Software Parking Lot", 20);
    when(parkingLot.isFull()).thenReturn(true);
    VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
    doReturn(false).when(vipParkingStrategy).isAllowOverPark(car);
    // when
    Receipt receipt = vipParkingStrategy.park(Collections.singletonList(parkingLot), car);
    // then
    assertEquals("粤C88888", receipt.getCarName());
    assertEquals("No Parking Lot", receipt.getParkingLotName());
    verify(vipParkingStrategy, times(1)).park(anyList(), any(Car.class));
  }

  @Test
  public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue() {

    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
    // given
    Car car = createMockCar("粤C8888A");
    when(carDao.isVip("粤C8888A")).thenReturn(true);
    // when
    boolean isAllowOverPark = vipParkingStrategy.isAllowOverPark(car);
    // then
    assertTrue(isAllowOverPark);
  }

  @Test
  public void
      testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse() {

    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
    // given
    Car car = createMockCar("粤C88888");
    when(carDao.isVip("粤C88888")).thenReturn(true);
    // when
    boolean isAllowOverPark = vipParkingStrategy.isAllowOverPark(car);
    // then
    assertFalse(isAllowOverPark);
  }

  @Test
  public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
    //given
    Car car = createMockCar("粤C8888A");
    when(carDao.isVip("粤C8888A")).thenReturn(false);
    //when
    boolean isAllowOverPark = vipParkingStrategy.isAllowOverPark(car);
    //then
    assertFalse(isAllowOverPark);
  }

  @Test
  public void
      testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
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
