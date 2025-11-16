package interface_adapter;

import interface_adapter.login.LoginState;

public class ViewModel<T> {

    private final String viewName;

    private T state;

    public ViewModel(String viewName){
        this.viewName = viewName;
    }

    public String getViewName(){
        return this.viewName;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }
}
