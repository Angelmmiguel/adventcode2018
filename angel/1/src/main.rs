use std::fs::File;
use std::io::BufReader;
use std::io::BufRead;

// Main function
fn main() {
  let file = File::open("input.txt").unwrap();
  // Load the file content
  let content = BufReader::new(&file);

  // Detecting repeated
  let mut results: Vec<i32> = vec![0];
  let mut repeated: Option<i32> = None;

  // Start the frecuency counter
  let mut frecuency = 0;

  // Read all lines
  let lines: Vec<_> = content.lines().map(|line| { line.unwrap() }).collect();

  for l in lines.iter() {
    let number: i32 = l.trim().parse()
        .expect("The number is not valid!");

    frecuency = frecuency + number;

    // Check if exits
    match repeated {
      None => {
        if results.contains(&frecuency) {
          repeated = Some(frecuency);
        }
      },
      Some(_) => ()
    }

    results.push(frecuency);
  }

  // Print the result
  println!("Final frecuency: {}", frecuency);

  if let Some(ref x) = repeated {
    println!("First repeated: {}", x);
  } else {
    let mut i = 0;

    loop {
      println!("Iteration {}", i);

      for l in lines.iter() {
        let number: i32 = l.trim().parse()
            .expect("The number is not valid!");

        frecuency = frecuency + number;

        // Check if exits
        match repeated {
          None => {
            if results.contains(&frecuency) {
              repeated = Some(frecuency);
              break;
            }
          },
          Some(_) => ()
        }

        results.push(frecuency);
      }

      i = i + 1;

      match repeated {
        None => (),
        Some(_) => break,
      }
    }
  }

  match repeated {
    Some(x) => println!("First repeated: {}", x),
    None => (),
  }
}
