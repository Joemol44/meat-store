package meat.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Meats {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long meatId;

private String meatType;
private String meatGrade;

@EqualsAndHashCode.Exclude
@ToString.Exclude
@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "meat_store_id", nullable = false)
private MeatStore meatStore;

}
