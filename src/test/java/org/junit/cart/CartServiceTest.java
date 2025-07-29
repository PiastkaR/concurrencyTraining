package org.junit.cart;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.order.Order;
import org.junit.order.OrderStatus;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @InjectMocks
    private CartService cartService; //ta klasa bedzie miala zaleznosci ktore bedziemy mockowac
    @Mock
    private CartHandler cartHandler; //klasa mockowana
    @Captor
    private ArgumentCaptor<Cart> argumentCaptor;
    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //when
        when(cartHandler.canHandleCart(cart)).thenReturn(true);
        Cart resultCart = cartService.processCart(cart);
        //then
        verify(cartHandler).sendToPrepare(cart);//sprawdzam czy metoda jest wywolana
        verify(cartHandler, times(1)).sendToPrepare(cart);
        then(cartHandler).should().sendToPrepare(cart);//BDD approach

        InOrder inOrder = Mockito.inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);//sprawdzam kolejnosc wywolan
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void processCartShouldNotSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //when
        when(cartHandler.canHandleCart(cart)).thenReturn(false);
        Cart resultCart = cartService.processCart(cart);
        //then
        verify(cartHandler, never()).sendToPrepare(cart);//NIGDY nie zostanie wywolana!
        then(cartHandler).should(never()).sendToPrepare(cart);//BDD
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //when
        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(false); //argument matcher
        Cart resultCart = cartService.processCart(cart);
        //then
        verify(cartHandler, never()).sendToPrepare(any(Cart.class));//NIGDY nie zostanie wywolana! nie mozna laczyc matcherow i wartosci!
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class));//BDD
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldReturnMultipleValues() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
        //when
        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(true, false, true, false); //argument matcher for multiple return
        //then
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
    }

    @Test
    void processCartShouldSendToPrepareWithlambdas() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        //when
        when(cartHandler.canHandleCart(argThat(cart1 -> cart1.getOrders().size() > 0))).thenReturn(true);
        Cart resultCart = cartService.processCart(cart);
        //then
        then(cartHandler).should().sendToPrepare(cart);//BDD approach
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void canHandleCartShouldThrowException() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //when
        given(cartHandler.canHandleCart(cart)).willThrow(IllegalStateException.class);
//        when(cartHandler.canHandleCart(cart)).thenThrow(IllegalStateException.class);
        //then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
    }

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);//liczba captorow musi odpowiadac liczbie captorow!
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);
        //then
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
//        verify(cartHandler).sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));//get values() when method is called many times
//        assertThat(argumentCaptor.getAllValues().size(), equalTo(2));//get values() when method is called many times
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void shouldDoNothignWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        //when
        when(cartHandler.canHandleCart(cart)).thenReturn(true);
//        doNothing().when(cartHandler).sendToPrepare(cart);
//        willDoNothing().given(cartHandler).sendToPrepare(cart);
//        willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart);
        Cart resultCart = cartService.processCart(cart);
        //then
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void shouldAnwserWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
        when(cartHandler.canHandleCart(cart)).then(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        });
        //when
        Cart resultCart = cartService.processCart(cart);
        doAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);
        willAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).given(cartHandler).canHandleCart(cart);
        //then
//        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders().size(), equalTo(0));
    }


}