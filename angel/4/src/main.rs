use std::fs::File;
use std::io::BufReader;
use std::io::BufRead;

// extern crate chrono;
// use chrono::prelude::*;

fn main() {
  let file = File::open("input.txt").unwrap();
  // Load the file content
  let content = BufReader::new(&file);

  // Read all lines
  let mut lines: Vec<_> = content.lines().map(|line| { line.unwrap() }).collect::<Vec<String>>();
  lines = lines.sort();

  for l in lines.iter() {
    println!("{}", l);
  }
}