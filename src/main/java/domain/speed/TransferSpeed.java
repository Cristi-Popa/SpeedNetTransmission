package domain.speed;

public interface TransferSpeed {
    public void sent(Runnable sent);
    public void receive(Runnable sent);
}
