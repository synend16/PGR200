package mockito.assignment1;

import mockito.TestDataProvider;
import mockito.Warehouse;
import mockito.WarehouseImpl;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrderTest {



    @Test
    /**
     * Assignment 1a
     * Classic testing: When warehouse has enough inventory
     * for a specific order:
     * 		- verify that the order is filled
     */
    public void orderIsFilledWhenWarehouseCanProvide() {
        // ARRANGE
        Warehouse normalWarehouse = TestDataProvider.getDefaultTestWarehouse();     // Oppretter et varehus
        Order fillableOrder = TestDataProvider.getFillableOrder(normalWarehouse);   // Oppretter en ordre det er kapasitet for å oppfyller i varehuset
        assertFalse(fillableOrder.isFilled());
        // ACT
        fillableOrder.fill(normalWarehouse);
        // ASSERT
        assertTrue(fillableOrder.isFilled());
    }

    @Test
    /**
     * Assignment 1b
     * Classic testing: When warehouse has enough inventory
     * for a specific order:
     * 		- verify that the inventory is updated accordingly
     */
    public void inventoryIsUpdatedWhenWarehouseFillsOrder() {
        Warehouse normalWarehouse = TestDataProvider.getDefaultTestWarehouse();
        Order fillableOrder = TestDataProvider.getFillableOrder(normalWarehouse);
        int initialInventory = normalWarehouse.getInventory(fillableOrder.getProductName());
        assertFalse(fillableOrder.isFilled());
        // ACT
        fillableOrder.fill(normalWarehouse);
        //ASSERT
        assertTrue(initialInventory == normalWarehouse.getInventory(fillableOrder.getProductName()) + fillableOrder.getQuantity());

    }

    @Test
    /**
     * Assignment 1c
     * Classic testing: When warehouse does not have enough inventory
     * for a specific order:
     * 		- verify that the order is not filled
     */
    public void orderIsNotFilledWhenWarehouseFailsToProvide() {
        Warehouse normalWarehouse = TestDataProvider.getDefaultTestWarehouse();
        Order notFillableOrder = TestDataProvider.getNonFillableOrder(normalWarehouse);
        assertFalse(notFillableOrder.isFilled());
        // ACT
        notFillableOrder.fill(normalWarehouse);
        // ASSERT
        assertFalse(notFillableOrder.isFilled());
    }

    @Test
    /**
     * Assignment 1d
     * Classic testing: When warehouse does not have enough inventory
     * for a specific order:
     * 		- verify that the warehouse inventory has not changed
     */
    public void inventoryIsUnchangedWhenOrderIsNotFilled() {
        Warehouse normalWarehouse = TestDataProvider.getDefaultTestWarehouse();
        Order notFillableOrder = TestDataProvider.getNonFillableOrder(normalWarehouse);
        int initialInventory = normalWarehouse.getInventory(notFillableOrder.getProductName());
        // ACT
        notFillableOrder.fill(normalWarehouse);
        // ASSERT
        assertTrue(initialInventory == normalWarehouse.getInventory(notFillableOrder.getProductName()));
    }

    @Test
    /**
     * Assignment 1e
     * Mock testing: When order is to be filled, mock a warehouse
     * providing enough inventory. Verify that both hasInventory()
     * and remove() is called in the warehouse.
     * Also make sure the order status is filled.
     */
    public void warehouseShouldCheckInventoryAndUpdateQuantityWhenNeeded() {
        Warehouse mockWarehouse = mock(Warehouse.class);
        Order order = TestDataProvider.getDefaultOrder();   // Oppretter en ordre det er kapasitet for å oppfyller i varehuset

        // We decide what the mockWarehouse will return using when/thenReturn
        when(mockWarehouse.hasInventory(order.getProductName(), order.getQuantity())).thenReturn(true);
        order.fill(mockWarehouse);

        verify(mockWarehouse).hasInventory(order.getProductName(), order.getQuantity());
        verify(mockWarehouse).remove(order.getProductName(), order.getQuantity());
        assertTrue(order.isFilled());


    }

    @Test
    /**
     * Assignment 1f
     * Mock testing: When order is to be filled and warehouse
     * cannot provide enough inventory, only hasInventory()
     * should be called in the warehouse.
     * Make sure remove() is NOT called.
     * Also make sure the order status is NOT filled.
     */
    public void warehouseShouldOnlyCheckInventoryWhenFillingIsImpossible() {
        // ARRANGE
        Warehouse mockWarehouse = mock(Warehouse.class);
        Order order = TestDataProvider.getDefaultOrder();
        // Bestemmer at mockwarehouse skal returnere false ved visse kriterier
        when(mockWarehouse.hasInventory(order.getProductName(), order.getQuantity())).thenReturn(false);
        //ACT
        order.fill(mockWarehouse);
        //ASSERT/VERIFY
        assertFalse(order.isFilled());
        verify(mockWarehouse).hasInventory(order.getProductName(), order.getQuantity());
        //Verifiser at remove ikke blir kalt
        verify(mockWarehouse, never()).remove(anyString(), anyInt());

        verifyNoMoreInteractions(mockWarehouse);
        assertFalse(order.isFilled());
    }

    @SuppressWarnings("rawtypes")
    @Test
    /**
     * Examples from lecture
     */
    public void examples() {
        List mockedList = mock(List.class);
        when(mockedList.get(0)).thenReturn("a");
        assertTrue(mockedList.get(0).equals("a"));
        assertNull(mockedList.get(1));
    }

}