#!/usr/bin/env ruby

twice = 0
three_times = 0
@box_ids = []
found_boxes = false

def similar_boxes?(id1, id2)
  differences = 0
  id1.chars.each_with_index do |char, index|
    next if id2.chars[index] == char
    differences += 1
    return false if differences > 1
  end
  differences.zero? ? false : true
end

def box_ids_common(id1, id2)
  common_string = ''
  id1.chars.each_with_index do |char, index|
    common_string += char if char == id2[index]
  end
  common_string
end

def found_similar_box(box_id)
  return unless @box_ids.any?

  @box_ids.each do |id|
    next unless similar_boxes?(id, box_id)

    common_letters = box_ids_common(id, box_id)
    return common_letters
  end

  nil
end

File.readlines('input.txt').each do |line|
  grouped_letters = line.split('').group_by { |x| x }.values
  twice += 1 if grouped_letters.find { |x| x.size == 2 }
  three_times += 1 if grouped_letters.find { |x| x.size == 3 }

  if !found_boxes && (common_letters = found_similar_box(line))
    puts "Part 2: #{common_letters}"
    found_boxes = true
  else
    @box_ids << line
  end
end

result = twice * three_times

puts "Part 1: #{result}"
