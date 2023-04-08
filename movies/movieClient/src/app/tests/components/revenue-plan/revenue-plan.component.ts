import { Component } from '@angular/core';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-revenue-plan',
  templateUrl: './revenue-plan.component.html',
  styleUrls: ['./revenue-plan.component.css']
})
export class RevenuePlanComponent {
  products: Product[] = [];
  usersForm!: FormGroup;

  constructor(private productService: ProductService, private fb: FormBuilder) { }

  ngOnInit() {
    this.usersForm = this.fb.group({
      users: this.fb.array([])
    });

    this.productService.getProductsSmall().subscribe(data => {
      data.forEach((e : Product) => {
        const user = this.fb.group({
          code: [e.code, Validators.required],
          name: [e.name, Validators.required],
          description: [e.description, Validators.required],
          price: [e.price, Validators.required],
          quantity: [e.quantity, Validators.required],
          inventoryStatus: [e.inventoryStatus, Validators.required],
          category: [e.category, Validators.required],
          image: [e.image, Validators.required],
          rating: [e.rating, Validators.required]
        });
        this.users.push(user);
      });
    });
  }

  addNewItem(index: number) {
    console.log(index);
    this.users.insert(index+1, this.getNewItemElement());
  }

  getNewItemElement() {
    return this.fb.group({
      code: [null, Validators.required],
      name: [null, Validators.required],
      description: [null, Validators.required],
      price: [null, Validators.required],
      quantity: [null, Validators.required],
      inventoryStatus: [null, Validators.required],
      category: [null, Validators.required],
      image: [null, Validators.required],
      rating: [null, Validators.required]
    });
  }

  get users() {
    return this.usersForm.get('users') as FormArray;
  }
}
