#!/usr/bin/env ruby

require 'set'

current_frecuency = 0

File.readlines('input.txt').each do |line|
  current_frecuency += line.to_i
end

puts "Part 1: #{current_frecuency}"

current_frecuency = 0
frecuencies = Set[]
frecuency_found = false

until frecuency_found
  File.readlines('input.txt').each do |line|
    frecuency_change = line.to_i
    current_frecuency += frecuency_change

    if frecuencies.include?(current_frecuency)
      puts "Part 2: #{current_frecuency}"
      frecuency_found = true
      break
    else
      frecuencies.add current_frecuency
    end
  end
end
