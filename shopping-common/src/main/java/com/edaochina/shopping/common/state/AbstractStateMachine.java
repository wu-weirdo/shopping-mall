package com.edaochina.shopping.common.state;

import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 状态机抽象类
 * @since : 2019/6/26 10:40
 */
public abstract class AbstractStateMachine<S, E> {

    private final Map<E, List<Handler>> map = new ConcurrentHashMap<>();
    private Function<Message<E>, S> defaultGetCurrentState;

    public Object handleEvent(Message<E> message) throws NullPointerException {
        if (map.size() == 0) {
            init();
        }
        Optional<Handler> optionalHandler = map.get(message.getPayload())
                .parallelStream().filter(handler -> handler.match(message)).findAny();
        return optionalHandler.orElseThrow(NullPointerException::new).action.apply(message);
    }

    private void addHandler(Handler handler) {
        map.compute(handler.e, (e, handlers) -> {
            if (handlers == null) {
                handlers = new ArrayList<>();
            }
            handlers.add(handler);
            return handlers;
        });
    }

    protected Builder withHandle() {
        return new Builder();
    }

    protected abstract void init();

    class Handler {
        private Function<Message<E>, S> getCurrentState;
        private List<S> acceptStates;
        private E e;
        private Function<Message<E>, Object> action;
        private Predicate<Message<E>> guards;

        boolean match(Message<E> message) {
            S current = getCurrentState == null ? defaultGetCurrentState.apply(message) : getCurrentState.apply(message);
            if (acceptStates.parallelStream().noneMatch(s -> s.equals(current))) {
                return false;
            }
            return guards == null || guards.test(message);
        }

        void setAction(Function<Message<E>, Object> action) {
            this.action = action;
        }
    }

    public class Builder {

        private Handler handler = new Handler();

        public Builder setDefaultGetCurrentState(Function<Message<E>, S> getCurrentState) {
            defaultGetCurrentState = getCurrentState;
            return this;
        }

        public Builder setCheckStates(List<S> acceptStates) {
            this.handler.acceptStates = acceptStates;
            return this;
        }

        public Builder setGetCurrentState(Function<Message<E>, S> getCurrentState) {
            this.handler.getCurrentState = getCurrentState;
            return this;
        }

        public Builder setEvent(E e) {
            this.handler.e = e;
            return this;
        }

        public Builder setAction(Function<Message<E>, Object> action) {
            this.handler.setAction(action);
            return this;
        }

        public Builder setGuards(Predicate<Message<E>> guards) {
            this.handler.guards = guards;
            return this;
        }

        public Builder build() {
            addHandler(handler);
            return this;
        }

        public Builder and() {
            return new Builder();
        }
    }


}
