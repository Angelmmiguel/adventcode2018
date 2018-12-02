use std::fs::File;
use std::io::BufReader;
use std::io::BufRead;
use std::io::{self, Write};
use std::collections::HashMap;
use std::vec::Vec;

fn difference_between_lines(line: &str, line_to_compare: &str) -> u32 {
  let mut counter: u32 = 0;
  let chars: Vec<char> = line_to_compare.chars().collect();

  for (index, ch) in line.chars().enumerate() {
    match chars.get(index) {
      None => (),
      Some(v) => {
        if *v != ch {
          counter += 1;
        }
      }
    }
  }
  counter
}

fn main() {
  let file = File::open("input.txt").unwrap();
  // Load the file content
  let content = BufReader::new(&file);

  // Start the frecuency counter
  let mut two = 0;
  let mut three = 0;

  // Read all lines
  let lines: Vec<_> = content.lines().map(|line| { line.unwrap() }).collect();

  for l in lines.iter() {
    let mut letters: HashMap<char, i32> = HashMap::new();

    for ch in l.chars() {
      let counter = letters.entry(ch).or_insert(0);
      *counter += 1;
    }

    // Three loop
    for val in letters.values() {
      if val == &3 {
        three += 1;
        break;
      }
    }

    // Two loop
    for val in letters.values() {
      if val == &2 {
        two += 1;
        break;
      }
    }
  }

  println!("The multipliers are: {} and {}", two, three);
  println!("The result is {}", two * three);

  // Second part
  println!("Identifying the two boxes!");

  let mut boxes: Vec<&str> = Vec::new();

  for l in lines.iter() {
    for o in lines.iter() {
      if l != o {
        
        if difference_between_lines(&l, &o) == 1 {
          boxes.push(&l);
          boxes.push(&o);
          break;
        }
      }
    }

    if boxes.len() > 0 {
      break;
    }
  }

  if boxes.len() > 0 {
    let first: &str = boxes.get(0).unwrap();
    let second: Vec<char> = boxes.get(1).unwrap().chars().collect();

    println!("The box ID is:");

    for (index, ch) in first.chars().enumerate() {
      match second.get(index) {
        None => (),
        Some(v) => {
          if *v == ch {
            print!("{}", ch);
          }
        }
      }
    }

    io::stdout().flush().unwrap();
    println!("");
  } else {
    println!("No boxes detected! :c")
  }
}