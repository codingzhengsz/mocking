package parking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ParkingLot.class, Calendar.class})
public class VipParkingStrategyPowerMockTest {

  @Test
  public void testCalculateHourlyPrice_givenSunday_thenPriceIsDoubleOfSundayPrice() {
    /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
     * by using PowerMock to mock static method */
    // given
    mockStatic(ParkingLot.class, Calendar.class);
    VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
    PowerMockito.when(ParkingLot.getBasicHourlyPrice()).thenReturn(25);
    PowerMockito.when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.SUNDAY);
    // when
    int totalCount = vipParkingStrategy.calculateHourlyPrice();
    // then
    assertEquals(50, totalCount);
  }

  @Test
  public void testCalculateHourlyPrice_givenNotSunday_thenPriceIsDoubleOfNonSundayPrice() {

    /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
     * by using PowerMock to mock static method */
    // given
    mockStatic(ParkingLot.class, Calendar.class);
    VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
    PowerMockito.when(ParkingLot.getBasicHourlyPrice()).thenReturn(20);
    PowerMockito.when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.MONDAY);
    // when
    int totalCount = vipParkingStrategy.calculateHourlyPrice();
    // then
    assertEquals(40, totalCount);
  }
}
